package com.marymamani.kriptoapp.data.datasource.local

import com.marymamani.kriptoapp.data.model.AppModel
import com.marymamani.kriptoapp.data.model.AppResourceModel

interface AppLocalDataSource {
    suspend fun getAppModel(appId: String): AppModel?
    suspend fun insertAllApps(apps: List<AppModel>)
    suspend fun getAllApps(): List<AppModel>
    suspend fun existApp(): Boolean
    suspend fun insertAllAppResource(appsResource: List<AppResourceModel>)
    suspend fun getAllAppsResource(): List<AppResourceModel>
    suspend fun existAppResource(): Boolean
}