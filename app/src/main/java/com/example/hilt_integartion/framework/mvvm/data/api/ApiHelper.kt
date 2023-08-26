package com.example.hilt_integartion.framework.mvvm.data.api

import com.example.hilt_integartion.framework.mvvm.data.model.User
import retrofit2.Response

interface ApiHelper {
    suspend fun getUsers(): Response<List<User>>

}