package com.marymamani.kriptoapp.data.model.mapper

import com.marymamani.kriptoapp.data.datasource.local.db.entities.AppEntity
import com.marymamani.kriptoapp.data.model.AppModel

fun AppEntity.toModel(): AppModel {
    return AppModel(
        id = this.id,
        packageName = this.packageName,
        appName = this.appName,
        version = this.version,
        isSystemApp = this.isSystemApp,
        installDate = this.installDate,
        lastUpdateDate = this.lastUpdateDate,
        uid = 0
    )
}

fun AppModel.toEntity(): AppEntity {
    return AppEntity(
        id = this.id,
        packageName = this.packageName,
        appName = this.appName,
        version = this.version,
        isSystemApp = this.isSystemApp,
        installDate = this.installDate,
        lastUpdateDate = this.lastUpdateDate
    )
}