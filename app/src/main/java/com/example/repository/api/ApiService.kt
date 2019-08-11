package com.example.repository.api

import com.example.repository.RepositoryData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{userName}/repos")
    fun getRepositoryFromUserName(@Path("userName") userName: String): Call<Array<RepositoryData>>
}