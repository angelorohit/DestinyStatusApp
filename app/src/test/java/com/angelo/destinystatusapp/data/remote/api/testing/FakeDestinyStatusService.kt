package com.angelo.destinystatusapp.data.remote.api.testing

import com.angelo.destinystatusapp.data.remote.api.DestinyStatusService
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.net.HttpURLConnection

class FakeDestinyStatusService : DestinyStatusService {
    var bungieHelpPosts: List<RemoteBungiePost>? = null
    var destiny2TeamPosts: List<RemoteBungiePost>? = null
    var destinyTheGamePosts: List<RemoteBungiePost>? = null

    var isSuccessful = true

    override suspend fun fetchBungieHelpPosts(): Response<List<RemoteBungiePost>> {
        return if (!isSuccessful) {
            makeErrorResponse()
        } else {
            Response.success(bungieHelpPosts)
        }
    }

    override suspend fun fetchDestiny2TeamPosts(): Response<List<RemoteBungiePost>> {
        return if (!isSuccessful) {
            makeErrorResponse()
        } else {
            Response.success(destiny2TeamPosts)
        }
    }

    override suspend fun fetchDestinyTheGamePosts(): Response<List<RemoteBungiePost>> {
        return if (!isSuccessful) {
            makeErrorResponse()
        } else {
            Response.success(destinyTheGamePosts)
        }
    }

    private fun makeErrorResponse() =
        Response.error<List<RemoteBungiePost>>(HttpURLConnection.HTTP_BAD_REQUEST, "".toResponseBody(null))
}
