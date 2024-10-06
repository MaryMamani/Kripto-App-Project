package com.marymamani.kriptoapp.domain.model.mapper

import com.marymamani.kriptoapp.data.model.AppModel
import com.marymamani.kriptoapp.domain.model.App

fun AppModel.toApp(): App {
    return App(
        id = this.id,
        appName = this.appName,
        isSystemApp = this.isSystemApp,
        lastUpdate = lastUpdateDate,
        icon = this.icon
    )
}