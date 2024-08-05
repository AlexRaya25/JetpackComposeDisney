package com.rayadev.domain.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val code = response.code
        val message = response.message

        if (code in 400..499) {
            throw IOException("Client Error: $code $message")
        } else if (code in 500..599) {
            throw IOException("Server Error: $code $message")
        }

        return response
    }
}

fun provideHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(ErrorInterceptor())
        .build()
}
