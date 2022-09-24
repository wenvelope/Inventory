package com.example.inventory.network

import com.example.inventory.spread.addLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object InventoryNetWork {

    private val userService = ServiceCreator.create(UserService::class.java)

    suspend fun getUserId(name:String,pwd:String) = userService.getUserId(name,pwd).await()



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