package com.marymamani.kriptoapp.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marymamani.kriptoapp.data.datasource.local.db.entities.AppEntity
import com.marymamani.kriptoapp.data.datasource.local.db.entities.AppResourceEntity

@Database(entities = [AppEntity::class, AppResourceEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun appDao(): AppDao
    abstract fun appResourceDao(): AppResourceDao
}