package com.example.inventory.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inventory.bean.MaterialBean

class HomeViewModel:ViewModel() {
    private val _repositoryArea  = MutableLiveData<String>()

    val repositoryArea:LiveData<String>
    get() = _repositoryArea

    fun setRepositoryArea(area:String){
        _repositoryArea.value = area
    }


    val materialList = mutableListOf<MaterialBean>()



}