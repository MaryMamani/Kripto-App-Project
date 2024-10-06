package com.marymamani.kriptoapp.domain.model

data class TopResourceUsageApps(
    val totalUsage: Int,
    val appList: List<DetailResourceUsageApp>
)
