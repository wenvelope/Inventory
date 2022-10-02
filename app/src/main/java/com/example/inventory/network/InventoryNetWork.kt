package com.example.inventory.network

import com.example.inventory.spread.addLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object InventoryNetWork {

    private val userService = ServiceCreator.create(UserService::class.java)
    private val materialService = ServiceCreator.create(MaterialService::class.java)

    suspend fun getUserId(name:String,pwd:String) = userService.getUserId(name,pwd).await()
    suspend fun addMaterial(materialJson:String) = materialService.addMaterial(materialJson).await()
    suspend fun checkMaterial(materialUid:String) = materialService.checkMaterial(materialUid).await()
    suspend fun selectByCategory(category:String) = materialService.selectByCategory(category).await()



    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine {
                continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T> , response: Response<T>) {
                    val body = response.body()
                    if(body!=null){
                        continuation.resume(body)
                    }else{
                        "response body is null".addLog()
                        continuation.resumeWithException( RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T> , t: Throwable) {
                    t.toString().addLog()
                    continuation.resumeWithException(t)
                }

            })
        }
    }
}