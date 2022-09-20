package com.example.itunesparsingapiassignment3.rest

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

//Implementing request and response interceptors

private const val TAG = "Interceptors"
class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
//Getting the request from the chain
        chain.proceed(
            chain.request().newBuilder().apply {
                addHeader("TOKEN", "This is your oAuthToken")
                addHeader("Env", "QA")
                addHeader("version", "1.0")
            }.build()
        )
  }


class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request()).apply {
            if (this.code == 200) {
                Log.d(TAG, "intercept: SUCCESS RESPONSE")
            } else {
                Log.e(TAG, "intercept: Failure Response")
            }
        }
    }
}