package com.example.inventory.repository

import androidx.lifecycle.liveData
import com.example.inventory.room.Material
import com.example.inventory.room.MaterialDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class Repository(private val materialDao: MaterialDao) {
    fun insertMaterial(vararg material: Material){
        material.forEach {
            materialDao.insertMaterial(it)
        }
    }

    fun selectAllLocalMaterial() = liveData(Dispatchers.IO){
        materialDao.selectAllMessages().collect{
            emit(it)
        }
    }
}