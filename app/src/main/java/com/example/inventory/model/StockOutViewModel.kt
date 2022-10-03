package com.example.inventory.model

import androidx.lifecycle.ViewModel
import com.example.inventory.MyApplication
import com.example.inventory.repository.Repository
import com.example.inventory.room.InventoryDataBase
import com.example.inventory.room.Material

class StockOutViewModel: ViewModel() {
    private val dataBase: InventoryDataBase by lazy { InventoryDataBase.getDataBase(MyApplication.context)}
    private val repository: Repository by lazy { Repository(dataBase.materialDao()) }


    fun selectOneMaterial(uid:String):Boolean{
        val materialList = repository.selectOneMaterial(uid)
        return materialList.isNotEmpty()
    }

    fun insertMaterial(material: Material):Boolean{
        return repository.insertMaterial(material)
    }
}