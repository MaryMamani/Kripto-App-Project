package com.marymamani.kriptoapp.data.repository

import com.marymamani.kriptoapp.data.datasource.local.AppLocalDataSource
import com.marymamani.kriptoapp.data.datasource.system.AppSystemDataSource
import com.marymamani.kriptoapp.data.model.AppModel
import com.marymamani.kriptoapp.data.model.AppResourceModel
import com.marymamani.kriptoapp.domain.repository.AppRepository

class AppRepositoryImpl(
    private val appLocalDataSource: AppLocalDataSource,
    private val appSystemDataSource: AppSystemDataSource
) : AppRepository {

    override suspend fun getAllApp(): List<AppModel> {
        return if (appLocalDataSource.existApp()) {
            appLocalDataSource.getAllApps().map {
                it.icon = appSystemDataSource.getIconAppByPackageName(it.packageName)
                it
            }
        } else {
            val apps = appSystemDataSource.getAllApp()
            appLocalDataSource.insertAllApps(apps)
            apps
        }
    }

    override suspend fun getAllAppResource(): List<AppResourceModel> {
        return if (appLocalDataSource.existAppResource()) {
            appLocalDataSource.getAllAppsResource().map {
                it.appName = appLocalDataSource.getAppModel(it.appId)?.appName ?: ""
                it.packageName = appLocalDataSource.getAppModel(it.appId)?.packageName ?: ""
                it.icon = appSystemDataSource.getIconAppByPackageName(it.packageName)
                it
            }
        } else {
            val appList = appLocalDataSource.getAllApps()
            val appResourceList = appList.map {
                val appResource = appSystemDataSource.getAppResourceById(it)
                appResource.appName = it.appName
                appResource.packageName = it.packageName
                appResource
            }.map {
                it.icon = appSystemDataSource.getIconAppByPackageName(it.packageName)
                it
            }
            appLocalDataSource.insertAllAppResource(appResourceList)
            appResourceList
        }
    }
}