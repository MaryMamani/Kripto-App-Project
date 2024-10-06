package com.marymamani.kriptoapp.data.model.mapper

import com.marymamani.kriptoapp.data.datasource.local.db.entities.AppResourceEntity
import com.marymamani.kriptoapp.data.model.AppResourceModel

fun AppResourceModel.toEntity(): AppResourceEntity {
    return AppResourceEntity(
        id = null,
        appId = appId,
        cpuUsagePercentage = cpuUsagePercentage,
        ramUsageMB = ramUsageMB,
        lastOpenedTimestamp = lastOpenedTimestamp,
        mobileDataUsageMB = mobileDataUsageMB,
        wifiDataUsageMB = wifiDataUsageMB
    )
}

fun AppResourceEntity.toModel(): AppResourceModel {
    return AppResourceModel(
        appId = appId,
        cpuUsagePercentage = cpuUsagePercentage,
        ramUsageMB = ramUsageMB,
        lastOpenedTimestamp = lastOpenedTimestamp,
        mobileDataUsageMB = mobileDataUsageMB,
        wifiDataUsageMB = wifiDataUsageMB,
        appName = "",
        packageName = ""
    )
}