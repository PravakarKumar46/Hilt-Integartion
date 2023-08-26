package com.example.hilt_integartion.framework.mvvm.data.api

import com.example.hilt_integartion.framework.mvvm.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

}