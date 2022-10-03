package com.example.inventory.repository

import androidx.lifecycle.liveData
import com.example.inventory.bean.MaterialByRe
import com.example.inventory.network.InventoryNetWork
import com.example.inventory.room.Material
import com.example.inventory.room.MaterialDao
import com.example.inventory.room.MaterialOut
import kotlinx.coroutines.Dispatchers

class Repository(private val materialDao: MaterialDao) {

    fun insertMaterial( material: Material):Boolean{
        return if(selectOneMaterial(material.uid).isEmpty()){
            materialDao.insertMaterial(material)
            true
        }else{
            false
        }
    }

    fun selectAllLocalMaterial() = liveData(Dispatchers.IO){
        materialDao.selectAllMessages().collect{
            emit(it)
        }
    }

    fun selectAllLocalOutMaterial() = liveData(Dispatchers.IO) {
        materialDao.selectOutMaterialList().collect{
            emit(it)
        }
    }



    fun selectOneMaterial(uid:String):List<Material>{
        return materialDao.selectOneMaterialByUid(uid)
    }

    fun takeLocalOutById(uid: String,repoUid: String){
        val repOut = MaterialOut(uid,"0",repoUid)
        materialDao.takeLocalOutById(repOut)
    }

    companion object{
        suspend fun checkMaterial(uid:String):String{
            val result = try {
                val response = InventoryNetWork.checkMaterial(uid).string()
                Result.success(response)
            }catch (e:Exception){
                Result.failure(e)
            }
            val end = result.getOrNull()
            return if(end == null){
                "网络错误"
            }else{
                when(end){
                    "200" ->{
                        "已入库"
                    }
                    "500"->{
                        "未入库"
                    }
                    else ->{
                        "已出库"
                    }

                }
            }
        }

        suspend fun selectAllByRe(uid:String):Result<MaterialByRe>{
            val result = try {
                val response = InventoryNetWork.selectByCategory(uid)
                Result.success(response)
            }catch (e:Exception){
                Result.failure(e)
            }
            return result
        }

        suspend fun takeOutById(uid:String,repoUid:String):Boolean{
            val result = try {
                val response = InventoryNetWork.takeOutById(uid,repoUid).string()
                Result.success(response)
            }catch (e:Exception){
                Result.failure(e)
            }
            val end = result.getOrNull()
            return if(end!=null){
                end == "200"
            }else{
                false
            }
        }

    }

}