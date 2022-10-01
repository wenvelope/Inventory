package com.example.inventory.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MaterialService {
    @GET("/material/add")
    fun addMaterial(@Query("itemMessage") materialJson:String): Call<ResponseBody>
}