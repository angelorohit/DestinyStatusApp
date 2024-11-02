package com.angelo.destinystatusapp.data.remote.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat
import com.angelo.destinystatusapp.data.remote.exception.NoConnectivityException
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import okhttp3.Request
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.net.HttpURLConnection

class NetworkAvailabilityInterceptorTest {
    private val mockContext: Context = mockk()
    private val mockConnectivityManager: ConnectivityManager = mockk()
    private val mockNetwork: Network = mockk()
    private val mockNetworkCapabilities: NetworkCapabilities = mockk()

    private val request = Request.Builder().url("https://www.realfakedoors.com/").build()
    private lateinit var networkAvailabilityInterceptor: NetworkAvailabilityInterceptor

    @BeforeEach
    fun setUp() {
        networkAvailabilityInterceptor = NetworkAvailabilityInterceptor(mockContext)

        mockkStatic(ContextCompat::class)
        every {
            ContextCompat.getSystemService(mockContext, ConnectivityManager::class.java)
        } returns mockConnectivityManager
        with(mockConnectivityManager) {
            every { activeNetwork } returns mockNetwork
            every { getNetworkCapabilities(mockNetwork) } returns mockNetworkCapabilities
        }
        with(mockNetworkCapabilities) {
            every { hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns true
            every { hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns true
        }
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Given network is available, then the expected response is returned`() {
        val response = networkAvailabilityInterceptor.intercept(request.toInterceptorChain())

        assertEquals(200, response.code)
        assertEquals("https://www.realfakedoors.com/", response.request.url.toString())
    }

    @Test
    fun `Given ConnectivityManager cannot be found, then an exception is thrown`() {
        every {
            ContextCompat.getSystemService(mockContext, ConnectivityManager::class.java)
        } returns null

        assertThrows<NoConnectivityException> {
            networkAvailabilityInterceptor.intercept(request.toInterceptorChain())
        }
    }

    @Test
    fun `Given NetworkCapabilities cannot be found, then an exception is thrown`() {
        every {
            mockConnectivityManager.getNetworkCapabilities(mockNetwork)
        } returns null

        assertThrows<NoConnectivityException> {
            networkAvailabilityInterceptor.intercept(request.toInterceptorChain())
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [NetworkCapabilities.TRANSPORT_WIFI, NetworkCapabilities.TRANSPORT_CELLULAR])
    fun `Given transport is WIFI or CELLULAR, then the expected response is returned`(transportType: Int) {
        every {
            mockNetworkCapabilities.hasTransport(transportType)
        } returns true

        val response = networkAvailabilityInterceptor.intercept(request.toInterceptorChain())

        assertEquals(HttpURLConnection.HTTP_OK, response.code)
        assertEquals("https://www.realfakedoors.com/", response.request.url.toString())
    }

    @ParameterizedTest
    @ValueSource(ints = [NetworkCapabilities.TRANSPORT_BLUETOOTH, NetworkCapabilities.TRANSPORT_ETHERNET])
    fun `Given transport is not WIFI or CELLULAR, then an exception is thrown`(transportType: Int) {
        with(mockNetworkCapabilities) {
            every { hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns false
            every { hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns false
            every { hasTransport(transportType) } returns true
        }

        assertThrows<NoConnectivityException> {
            networkAvailabilityInterceptor.intercept(request.toInterceptorChain())
        }
    }
}
