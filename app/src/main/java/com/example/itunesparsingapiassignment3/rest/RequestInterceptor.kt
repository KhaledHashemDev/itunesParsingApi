package com.example.itunesparsingapiassignment3.rest

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }
}


class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO()
    }
}