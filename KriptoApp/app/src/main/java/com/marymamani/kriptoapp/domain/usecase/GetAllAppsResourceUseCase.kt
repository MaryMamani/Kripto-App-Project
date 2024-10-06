package com.marymamani.kriptoapp.domain.usecase

import com.marymamani.kriptoapp.data.model.AppResourceModel
import com.marymamani.kriptoapp.domain.repository.AppRepository

class GetAllAppsResourceUseCase(private val appRepository: AppRepository) {
    suspend operator fun invoke(): List<AppResourceModel>? {
        return appRepository.getAllAppResource()
    }
}