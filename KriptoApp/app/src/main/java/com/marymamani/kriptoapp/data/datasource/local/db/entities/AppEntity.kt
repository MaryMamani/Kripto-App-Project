package com.marymamani.kriptoapp.data.datasource.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_entity")
data class AppEntity(
    @PrimaryKey val id: String,
    val packageName: String,
    val appName: String,
    val version: String,
    val isSystemApp: Boolean,
    val installDate: Long? = null,
    val lastUpdateDate: Long
)

