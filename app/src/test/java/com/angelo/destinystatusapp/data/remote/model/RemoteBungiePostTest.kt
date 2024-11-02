package com.angelo.destinystatusapp.data.remote.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class RemoteBungiePostTest {
    @Test
    fun `getLastRepost should return the original post if there are no reposts`() {
        val post = RemoteBungiePost(id = "1")

        assertEquals(post, post.getLastRepost())
    }

    @Test
    fun `getLastRepost should return the last repost in the chain`() {
        val lastRepost = RemoteBungiePost(id = "3")
        val repost = RemoteBungiePost(id = "2", repost = lastRepost)
        val originalPost = RemoteBungiePost(id = "1", repost = repost)

        assertEquals(lastRepost, originalPost.getLastRepost())
    }

    @Test
    fun `getLargeImageUrl should return a modified URL with format and size parameters when valid`() {
        val media = RemoteBungiePostMedia(
            imageUrl = "https://example.com/image.png",
            sizes = RemoteBungiePostMediaSizes(
                large = RemoteBungiePostMediaSize(width = 800, height = 600)
            )
        )

        val expectedUrl = "https://example.com/image?format=png&name=large"
        assertEquals(expectedUrl, media.getLargeImageUrl())
    }

    @ParameterizedTest
    @ValueSource(strings = ["https://example.com/image", "https://example.com/image/"])
    fun `getLargeImageUrl should assume jpg format if no extension in URL`(testImageUrl: String) {
        val media = RemoteBungiePostMedia(
            imageUrl = testImageUrl,
            sizes = RemoteBungiePostMediaSizes(
                large = RemoteBungiePostMediaSize(width = 800, height = 600)
            )
        )

        val expectedUrl = "https://example.com/image?format=jpg&name=large"
        assertEquals(expectedUrl, media.getLargeImageUrl())
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `getLargeImageUrl should return null if imageUrl is blank`(testImageUrl: String) {
        val mediaWithNullUrl = RemoteBungiePostMedia(
            imageUrl = testImageUrl,
            sizes = RemoteBungiePostMediaSizes(
                large = RemoteBungiePostMediaSize(width = 800, height = 600)
            )
        )

        assertNull(mediaWithNullUrl.getLargeImageUrl())
    }

    @Test
    fun `getLargeImageUrl should return null if imageUrl is null`() {
        val mediaWithNullUrl = RemoteBungiePostMedia(
            imageUrl = null,
            sizes = RemoteBungiePostMediaSizes(
                large = RemoteBungiePostMediaSize(width = 800, height = 600)
            )
        )

        assertNull(mediaWithNullUrl.getLargeImageUrl())
    }

    @Test
    fun `getLargeImageUrl should return null if the large size is invalid`() {
        val mediaWithInvalidSize = RemoteBungiePostMedia(
            imageUrl = "https://example.com/image.png",
            sizes = RemoteBungiePostMediaSizes(
                large = RemoteBungiePostMediaSize(width = 0, height = 600)
            )
        )

        assertNull(mediaWithInvalidSize.getLargeImageUrl())
    }
}
