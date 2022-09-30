package com.example.inventory.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(version = 1, entities = [Material::class], exportSchema = false)
abstract class InventoryDataBase:RoomDatabase() {

    abstract fun materialDao():MaterialDao

    companion object{
        private var instance:InventoryDataBase ?=null
        @Synchronized
        fun getDataBase(context: Context):InventoryDataBase{
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,InventoryDataBase::class.java,"Inventory_database")
                .fallbackToDestructiveMigration()
                .build().apply {
                    instance = this
                }
        }
    }
}