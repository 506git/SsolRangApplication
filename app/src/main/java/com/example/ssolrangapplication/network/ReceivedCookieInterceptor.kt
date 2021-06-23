package com.example.ssolrangapplication.network

import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookieInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()){
            for (header in originalResponse.headers("set-Cookie")){
                if (header.isNotEmpty()){

                }
            }
        }
        return originalResponse
    }
}