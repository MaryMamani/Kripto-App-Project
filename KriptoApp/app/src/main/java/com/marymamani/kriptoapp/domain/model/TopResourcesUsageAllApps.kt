package com.marymamani.kriptoapp.domain.model

data class TopResourcesUsageAllApps(
    val topMemoryUsage: TopResourceUsageApps,
    val topCpuUsage: TopResourceUsageApps,
    val topMobileDataUsage: TopResourceUsageApps,
    val topWifiDataUsage: TopResourceUsageApps
)