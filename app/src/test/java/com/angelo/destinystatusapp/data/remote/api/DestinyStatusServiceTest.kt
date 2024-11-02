package com.angelo.destinystatusapp.data.remote.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat
import com.angelo.destinystatusapp.data.remote.exception.NoConnectivityException
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import retrofit2.Response

class DestinyStatusServiceTest {
    private val mockContext: Context = mockk()

    @BeforeEach
    fun setUp() {
        mockkStatic(ContextCompat::class)
        every {
            ContextCompat.getSystemService(mockContext, ConnectivityManager::class.java)
        } returns mockk {
            every { activeNetwork } returns mockk()
            every { getNetworkCapabilities(any()) } returns mockk {
                every { hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns true
                every { hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns true
            }
        }
    }

    @Test
    fun `When fetchBungieHelpPosts() is called, then a successful response is returned`() = runBlocking {
        val response: Response<List<RemoteBungiePost>> =
            ServiceClient.createDestinyStatusService(mockContext).fetchBungieHelpPosts()

        assertTrue { response.isSuccessful }
        assertNotNull(response.body())
        assertTrue(response.body()!!.isNotEmpty())
    }

    @Test
    fun `When fetchDestiny2TeamPosts() is called, then a successful response is returned`() = runBlocking {
        val response = ServiceClient.createDestinyStatusService(mockContext).fetchDestiny2TeamPosts()

        assertTrue { response.isSuccessful }
        assertNotNull(response.body())
        assertTrue(response.body()!!.isNotEmpty())
    }

    @Test
    fun `When fetchDestinyTheGamePosts() is called, then a successful response is returned`() = runBlocking {
        val response = ServiceClient.createDestinyStatusService(mockContext).fetchDestinyTheGamePosts()

        assertTrue { response.isSuccessful }
        assertNotNull(response.body())
        assertTrue(response.body()!!.isNotEmpty())
    }

    @Test
    fun `Given network connection is unavailable, then an exception is thrown`(): Unit = runBlocking {
        every {
            ContextCompat.getSystemService(mockContext, ConnectivityManager::class.java)
        } returns null

        assertThrows<NoConnectivityException> {
            ServiceClient.createDestinyStatusService(mockContext).fetchDestinyTheGamePosts()
        }
    }
}
