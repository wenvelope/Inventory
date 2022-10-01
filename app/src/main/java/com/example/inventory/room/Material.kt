package com.example.inventory.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "materials")
data class Material(
    //编号
    @PrimaryKey
    val uid: String,
    //名字
    val name: String,
    //状态
    val state: String,
    //入库号
    val inboundNum: String,
    //所属库位uid
    val repoBeanUid: String,
    //类型
    val category: String,
    //描述
    val description: String? = "无",
    //生产日期
    val born: String
)