package com.marymamani.kriptoapp.domain.model

data class AppUsage(
    val id: String,
    val appId: String,
    val storageUsage: Double,
    val batteryUsage: Double,
    val totalOpened: Long,
    val lastOpened: Long,
    val totalTimeInForeground: Long,
    val networkUsage: Double? = null
)
