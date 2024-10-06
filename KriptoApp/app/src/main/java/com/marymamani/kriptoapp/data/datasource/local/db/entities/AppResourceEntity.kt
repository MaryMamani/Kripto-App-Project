package com.marymamani.kriptoapp.data.datasource.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_resource_entity")
data class AppResourceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val appId: String,
    val cpuUsagePercentage: Float,
    val ramUsageMB: Int,
    val lastOpenedTimestamp: Long,
    val mobileDataUsageMB: Long,
    val wifiDataUsageMB: Long,
)
