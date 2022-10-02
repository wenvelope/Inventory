package com.example.inventory.bean

import com.example.inventory.room.Material

data class MaterialByRe(
    val uid:String,
    val name:String,
    val materialList:MutableList<Material>
) {

}
