package com.example.inventory.repository

import androidx.lifecycle.liveData
import com.example.inventory.room.Material
import com.example.inventory.room.MaterialDao
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

    fun selectOneMaterial(uid:String):List<Material>{
        return materialDao.selectOneMaterialByUid(uid)
    }

}