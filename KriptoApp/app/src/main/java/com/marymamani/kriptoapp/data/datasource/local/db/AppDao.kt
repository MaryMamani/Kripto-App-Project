package com.marymamani.kriptoapp.data.datasource.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marymamani.kriptoapp.data.datasource.local.db.entities.AppEntity

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppEntity(appEntity: AppEntity)

    @Query("SELECT * FROM app_entity WHERE id = :appId")
    suspend fun getAppById(appId: String): AppEntity?

    @Query("SELECT * FROM app_entity")
    suspend fun getAllApps(): List<AppEntity>

    @Query("SELECT COUNT(*) FROM app_entity")
    suspend fun existApps(): Int
}