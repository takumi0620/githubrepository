package com.example.repository.list.model.api

import com.example.repository.list.model.data.RepositoryData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{userName}/repos")
    fun getRepositoryFromUserName(@Path("userName") userName: String): Call<Array<RepositoryData>>
}