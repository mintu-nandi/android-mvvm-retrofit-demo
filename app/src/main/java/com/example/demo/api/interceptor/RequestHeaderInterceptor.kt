package com.example.demo.api.interceptor

import com.example.demo.util.Constants.API_VERSION
import com.example.demo.util.Constants.APP_ID
import com.example.demo.util.Constants.APP_VERSION
import com.example.demo.util.Constants.CONTENT_TYPE
import okhttp3.Interceptor
import okhttp3.Response

class RequestHeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("AppId", APP_ID)
            .addHeader("Content-Type", CONTENT_TYPE)
            .addHeader("appVersion", APP_VERSION)
            .addHeader("apiVersion", API_VERSION)
            .build()
        return chain.proceed(request)
    }
}