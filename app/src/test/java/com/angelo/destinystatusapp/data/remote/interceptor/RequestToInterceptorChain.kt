package com.angelo.destinystatusapp.data.remote.interceptor

import okhttp3.Call
import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

fun Request.toInterceptorChain() = object : Interceptor.Chain {
    override fun proceed(request: Request): Response {
        return Response.Builder()
            .code(HttpURLConnection.HTTP_OK)
            .protocol(Protocol.HTTP_1_1)
            .message("")
            .request(request)
            .build()
    }

    override fun request(): Request = this@toInterceptorChain

    override fun call(): Call { throw UnsupportedOperationException() }

    override fun connection(): Connection? = null

    override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain = this

    override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain = this

    override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain = this

    override fun readTimeoutMillis(): Int = 0

    override fun writeTimeoutMillis(): Int = 0

    override fun connectTimeoutMillis(): Int = 0
}
