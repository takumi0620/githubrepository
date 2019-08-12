package com.example.util

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {

    /**
     * ネットワーク接続確認
     * @return true: online false: offline
     */
    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info?.isConnected ?: false
    }
}