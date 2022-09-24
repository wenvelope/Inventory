package com.example.inventory.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("user/login")
    fun getUserId(@Query("email") username:String, @Query("password") pwd:String): Call<ResponseBody>
}