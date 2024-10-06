package com.marymamani.kriptoapp.data.model

import android.graphics.drawable.Drawable

data class AppResourceModel(
    val appId: String,
    val cpuUsagePercentage: Float,
    val ramUsageMB: Int,
    val lastOpenedTimestamp: Long,
    val mobileDataUsageMB: Long,
    val wifiDataUsageMB: Long,
    var appName: String,
    var packageName: String,
    var icon: Drawable? = null
)
