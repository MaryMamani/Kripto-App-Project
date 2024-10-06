package com.marymamani.kriptoapp.domain.usecase

import com.marymamani.kriptoapp.domain.model.App
import com.marymamani.kriptoapp.domain.model.mapper.toApp
import com.marymamani.kriptoapp.domain.repository.AppRepository


class GetAllAppsUseCase(private val appRepository: AppRepository) {
    suspend operator fun invoke(): List<App> {
        return appRepository.getAllApp().map { appModel ->
            appModel.toApp()
        }
    }
}