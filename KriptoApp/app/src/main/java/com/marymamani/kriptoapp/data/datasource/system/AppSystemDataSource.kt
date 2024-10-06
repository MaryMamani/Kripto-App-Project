package com.marymamani.kriptoapp.data.datasource.system

import android.graphics.drawable.Drawable
import com.marymamani.kriptoapp.data.model.AppModel
import com.marymamani.kriptoapp.data.model.AppResourceModel

interface AppSystemDataSource {

    fun getAllApp(): List<AppModel>
    fun getAppResourceById(appModel: AppModel): AppResourceModel
    fun getIconAppByPackageName(packageName: String): Drawable
}