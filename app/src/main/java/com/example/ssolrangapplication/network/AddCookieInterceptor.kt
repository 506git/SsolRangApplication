package com.example.ssolrangapplication.network

import okhttp3.Interceptor
import okhttp3.Response

class AddCookieInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
//        builder.addHeader("COokie",)
        return chain.proceed(builder.build())
    }
}