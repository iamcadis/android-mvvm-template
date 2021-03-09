package com.apps.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.InetSocketAddress
import java.net.Socket
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHelper @Inject constructor(@ApplicationContext private val context: Context) {

    @Suppress("DEPRECATION")
    val isNetworkConnected: Boolean
        get() {
            var isConnected = false
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getNetworkCapabilities(activeNetwork)?.run {
                        isConnected = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                } else {
                    val localNetworkInfo =
                            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
                    if (localNetworkInfo != null && localNetworkInfo.isConnectedOrConnecting) {
                        isConnected = true
                    }
                }
            }

            if (isConnected) {
                val socket = Socket()
                try {
                    socket.connect(InetSocketAddress("www.google.com", 443), 6000)
                    isConnected = true
                } catch (ex: UnknownHostException) {
                    isConnected = false
                    ex.printStackTrace()
                } catch (ex: Exception) {
                    isConnected = false
                    ex.printStackTrace()
                } finally {
                    try {
                        socket.close()
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }

            return isConnected
        }
}