package com.example.repository.api

import com.example.Constant
import com.example.repository.RepositoryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException

object ApiManager {
    interface CallbackListener<T> {
        fun onSuccess(result: T)
        fun onFailure(message: String)
        fun onFinally()
    }

    fun getRepositoryFromUserName(userName: String, listener: CallbackListener<Array<RepositoryData>>) {
        getApiService().getRepositoryFromUserName(userName).enqueue(ApiCallback(listener))
    }

    private fun getApiService(): ApiService {
        return Retrofit
            .Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    class ApiCallback<T>(private val listener: CallbackListener<T>): Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            try {
                val httpStatusCode = response.raw().code()
                if (httpStatusCode in 200 until 400) {
                    val result = response.body() ?: run {
                        throw RuntimeException("null")
                    }
                    listener.onSuccess(result)
                } else {
                    if (httpStatusCode >= 500) {
                        // 500番台
                        listener.onFailure("server error")
                    } else {
                        // 400番台
                        listener.onFailure("error")
                    }
                }
            } finally {
                listener.onFinally()
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            try {
                listener.onFailure(t.message ?: "")
            } finally {
                listener.onFinally()
            }
        }
    }
}