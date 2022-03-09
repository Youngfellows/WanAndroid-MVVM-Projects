package com.bbq.net.interceptor

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

/**
 * 日志拦截工具
 */
class HttpLogger : HttpLoggingInterceptor.Logger {

    private val TAG: String = this.javaClass.simpleName

    override fun log(message: String) {
        Log.w(TAG, "$message")
    }
}