package com.rayadev.jetpackcomposedisney.utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code in 400..499) {
            throw IOException("Client Error: ${response.code} ${response.message}")
        } else if (response.code in 500..599) {
            throw IOException("Server Error: ${response.code} ${response.message}")
        }

        return response
    }
}

fun provideHttpClient(): OkHttpClient {

    return OkHttpClient.Builder()
        .addInterceptor(ErrorInterceptor())
        .build()
}
