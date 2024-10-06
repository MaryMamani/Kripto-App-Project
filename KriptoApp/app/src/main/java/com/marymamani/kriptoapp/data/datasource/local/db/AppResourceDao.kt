package com.marymamani.kriptoapp.data.datasource.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marymamani.kriptoapp.data.datasource.local.db.entities.AppResourceEntity

@Dao
interface AppResourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppResourceEntity(appResourceEntity: AppResourceEntity)

    @Query("SELECT * FROM app_resource_entity")
    suspend fun getAllAppsResource(): List<AppResourceEntity>

    @Query("SELECT COUNT(*) FROM app_resource_entity")
    suspend fun existAppsResource(): Int
}