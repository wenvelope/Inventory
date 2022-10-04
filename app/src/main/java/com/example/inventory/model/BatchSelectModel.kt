package com.example.inventory.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.inventory.MyApplication
import com.example.inventory.repository.Repository
import com.example.inventory.room.InventoryDataBase
import com.example.inventory.room.InventoryDataBase_Impl
import com.example.inventory.room.Material

class BatchSelectModel:ViewModel() {
    private val dataBase: InventoryDataBase by lazy { InventoryDataBase.getDataBase(MyApplication.context)}
    private val repository: Repository by lazy { Repository(dataBase.materialDao()) }

    private val _materialInBoundNum = MutableLiveData<String>()

    val materialInBoundNum = Transformations.switchMap(_materialInBoundNum){
       repository.selectAllByInBoundNum(it)
    }

    fun selectAllByInBoundNum(num:String){
        _materialInBoundNum.value = num
    }

    val materialInBoundNumList = mutableListOf<Material>()
}