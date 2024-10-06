package com.marymamani.kriptoapp.domain.model

data class NetworkUsage(
    val id: String,
    val appId: String,
    val mobileDataUsage: Double,
    val wifiDataUsage: Double,
)
