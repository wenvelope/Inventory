package com.example.inventory.network

import com.example.inventory.bean.MaterialByRe
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MaterialService {
    @GET("/material/add")
    fun addMaterial(@Query("itemMessage") materialJson:String): Call<ResponseBody>

    @GET("/material/checkById")
    fun checkMaterial(@Query("itemId") uid:String):Call<ResponseBody>

    @GET("material/findByCategory")
    fun selectByCategory(@Query("itemCategory") category:String):Call<MaterialByRe>

    @GET("material/takeOutById")
    fun takeOutById(@Query("itemId") uid:String,@Query("itemRepoBeanId") repoId:String):Call<ResponseBody>
}