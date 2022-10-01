package com.example.inventory.model

import androidx.lifecycle.*
import com.example.inventory.MyApplication
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

    val name = MutableLiveData<String>()


    val materialList = mutableListOf<Material>()

    private val _materialData = MutableLiveData<Any>()

    val materialData = Transformations.switchMap(_materialData){
        repository.selectAllLocalMaterial()
    }

    fun getAllLocalMaterial(){
        _materialData.value = _materialData
    }

    suspend fun insertOneMaterial(material: Material):Boolean{
        return repository.insertMaterial(material)
    }


}