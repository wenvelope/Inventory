package com.example.inventory.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.MyApplication
import com.example.inventory.bean.MaterialByRe
import com.example.inventory.repository.Repository
import com.example.inventory.room.InventoryDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StockViewModel:ViewModel() {
    private val dataBase: InventoryDataBase by lazy { InventoryDataBase.getDataBase(MyApplication.context)}
    private val repository: Repository by lazy { Repository(dataBase.materialDao()) }

    suspend fun selectMaterialListByRe(uid:String): Result<MaterialByRe> {
        val response = withContext(Dispatchers.IO){
          Repository.selectAllByRe(uid)
        }
        return response
    }






}