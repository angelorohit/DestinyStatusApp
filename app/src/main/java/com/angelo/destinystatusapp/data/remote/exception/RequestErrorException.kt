package com.angelo.destinystatusapp.data.remote.exception

data class RequestErrorException(val httpStatusCode: Int, val errorBody: String) : Exception()
