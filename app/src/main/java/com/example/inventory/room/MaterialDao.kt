package com.example.inventory.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDao {
    @Insert
    fun insertMaterial(material: Material)

    @Query("select * from materials")
    fun selectAllMessages(): Flow<List<Material>>

    @Query("select * from materials where uid = :uid")
    fun selectOneMaterialByUid(uid:String):List<Material>

    @Query("select * from materials where state = '0' ")
    fun selectOutMaterialList():Flow<List<Material>>

    @Update(entity = Material::class)
    fun takeLocalOutById(repOut:MaterialOut)

    @Query("select * from materials where inboundNum = :num")
    fun selectAllByInboundNum(num:String):List<Material>



}