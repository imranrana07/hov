package com.imran.hov.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.imran.hov.core.ApplicationClass
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

fun isInternetAvailable(): Boolean {
    var result = false
    val connectivityManager =
        ApplicationClass.appContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }
    return result
}