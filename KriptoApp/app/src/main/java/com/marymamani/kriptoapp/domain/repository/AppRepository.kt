package com.marymamani.kriptoapp.domain.repository

import com.marymamani.kriptoapp.data.model.AppModel
import com.marymamani.kriptoapp.data.model.AppResourceModel

interface AppRepository {
    suspend fun getAllApp(): List<AppModel>
    suspend fun getAllAppResource(): List<AppResourceModel>
}