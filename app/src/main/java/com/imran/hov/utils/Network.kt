package com.imran.hov.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

val interceptor: Interceptor = Interceptor { chain ->

    var request: Request = chain.request()
    request = request.newBuilder()
//            .addHeader("User-Agent", "wad4")
        .build()

    chain.proceed(request)
}
val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
class ApiException(message: String): IOException(message)
