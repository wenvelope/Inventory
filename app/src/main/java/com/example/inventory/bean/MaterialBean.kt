package com.example.inventory.bean

data class MaterialBean(
    //编号
    val uid: String,
    //名字
    val name: String,
    //状态
    val state: Int,
    //入库号
    val inboundNum: Int,
    //所属库位uid
    val repoBeanUid: String,
    //类型
    val category: String,
    //描述
    val describe: String? = "无",
    //生产日期
    val born: String
)
