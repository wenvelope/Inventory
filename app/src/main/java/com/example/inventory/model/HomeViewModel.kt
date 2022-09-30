package com.example.inventory.model

import android.service.autofill.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.inventory.MyApplication
import com.example.inventory.bean.MaterialBean
import com.example.inventory.repository.Repository
import com.example.inventory.room.InventoryDataBase
import com.example.inventory.room.Material

class HomeViewModel:ViewModel() {
    private val dataBase:InventoryDataBase by lazy { InventoryDataBase.getDataBase(MyApplication.context)}
    private val repository:Repository by lazy { Repository(dataBase.materialDao()) }

    private val _repositoryArea  = MutableLiveData<String>()

    val repositoryArea:LiveData<String>
    get() = _repositoryArea

    fun setRepositoryArea(area:String){
        _repositoryArea.value = area
    }


    val materialList = mutableListOf<Material>()

    private val _materialData = MutableLiveData<Any>()

    val materialData = Transformations.switchMap(_materialData){
        repository.selectAllLocalMaterial()
    }

    fun getAllLocalMaterial(){
        _materialData.value = _materialData
    }

    fun insertMaterial(vararg material: Material){
        repository.insertMaterial(*material)
    }

}