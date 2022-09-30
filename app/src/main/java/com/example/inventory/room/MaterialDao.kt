package com.example.inventory.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDao {
    @Insert
    fun insertMaterial(material: Material)

    @Query("select * from materials")
    fun selectAllMessages(): Flow<List<Material>>

    @Query("select * from materials where uid = :uid")
    fun selectOneMaterialByUid(uid:String):List<Material>

}