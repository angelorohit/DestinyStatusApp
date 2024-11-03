package com.angelo.destinystatusapp.data.remote

import com.angelo.destinystatusapp.data.model.BungieChannelType
import com.angelo.destinystatusapp.data.remote.api.DestinyStatusService
import com.angelo.destinystatusapp.data.remote.api.testing.FakeDestinyStatusService
import com.angelo.destinystatusapp.data.remote.exception.RequestErrorException
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.junit5.KoinTestExtension
import java.net.HttpURLConnection

class RemoteDataSourceImplTest : KoinTest {
    private val fakeDestinyStatusService = FakeDestinyStatusService()
    private lateinit var remoteDataSource: RemoteDataSource

    @JvmField
    @RegisterExtension
    @Suppress("Unused")
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                single<DestinyStatusService> { fakeDestinyStatusService }
                single<RemoteDataSource> { RemoteDataSourceImpl() }
            },
        )
    }

    @BeforeEach
    fun setUp() {
        remoteDataSource = get()
    }

    @ParameterizedTest
    @EnumSource(BungieChannelType::class)
    fun `When fetchPosts is successfully called, then the expected list of RemoteBungiePost should be returned`(
        channelType: BungieChannelType,
    ) = runBlocking {
        val expected = when (channelType) {
            BungieChannelType.BungieHelp -> {
                val posts = (1..30).map { RemoteBungiePost(id = "$it") }
                fakeDestinyStatusService.bungieHelpPosts = posts
                posts
            }

            BungieChannelType.Destiny2Team -> {
                val posts = (30..60).map { RemoteBungiePost(id = "$it") }
                fakeDestinyStatusService.destiny2TeamPosts = posts
                posts
            }

            BungieChannelType.DestinyTheGame -> {
                val posts = (60..90).map { RemoteBungiePost(id = "$it") }
                fakeDestinyStatusService.destinyTheGamePosts = posts
                posts
            }
        }

        val result = remoteDataSource.fetchPosts(channelType)

        assertEquals(expected.take(20), result)
    }

    @ParameterizedTest
    @EnumSource(BungieChannelType::class)
    fun `When fetchPosts is successfully called with an empty response, then a RequestErrorException should be thrown`(
        channelType: BungieChannelType,
    ) = runBlocking {
        val exception = assertThrows<RequestErrorException> { remoteDataSource.fetchPosts(channelType) }

        assertEquals(HttpURLConnection.HTTP_OK, exception.httpStatusCode)
        assertEquals("Empty response body", exception.errorBody)
    }

    @ParameterizedTest
    @EnumSource(BungieChannelType::class)
    fun `When fetchPosts fails with an error, then a RequestErrorException should be thrown`(
        channelType: BungieChannelType,
    ): Unit = runBlocking {
        fakeDestinyStatusService.isSuccessful = false

        val exception = assertThrows<RequestErrorException> { remoteDataSource.fetchPosts(channelType) }

        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, exception.httpStatusCode)
        assertTrue(exception.errorBody.isEmpty())
    }
}
