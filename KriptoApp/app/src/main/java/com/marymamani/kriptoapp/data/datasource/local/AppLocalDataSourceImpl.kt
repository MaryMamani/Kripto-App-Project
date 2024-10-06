package com.marymamani.kriptoapp.data.datasource.local

import com.marymamani.kriptoapp.data.datasource.local.db.AppDao
import com.marymamani.kriptoapp.data.datasource.local.db.AppResourceDao
import com.marymamani.kriptoapp.data.model.AppModel
import com.marymamani.kriptoapp.data.model.AppResourceModel
import com.marymamani.kriptoapp.data.model.mapper.toEntity
import com.marymamani.kriptoapp.data.model.mapper.toModel

class AppLocalDataSourceImpl(
    private val appDao: AppDao,
    private val appResourceDao: AppResourceDao
) : AppLocalDataSource {
    override suspend fun getAppModel(appId: String): AppModel? {
        return appDao.getAppById(appId)?.toModel()
    }

    override suspend fun insertAllApps(apps: List<AppModel>) {
        apps.map {
            appDao.insertAppEntity(it.toEntity())
        }
    }

    override suspend fun getAllApps(): List<AppModel> {
        return appDao.getAllApps().map {
            it.toModel()
        }
    }

    override suspend fun existApp(): Boolean {
        return appDao.existApps() > 0
    }

    override suspend fun insertAllAppResource(appsResource: List<AppResourceModel>) {
        appsResource.map {
            appResourceDao.insertAppResourceEntity(it.toEntity())
        }
    }

    override suspend fun getAllAppsResource(): List<AppResourceModel> {
        return appResourceDao.getAllAppsResource().map {
            it.toModel()
        }
    }

    override suspend fun existAppResource(): Boolean {
        return appResourceDao.existAppsResource() > 0
    }
}