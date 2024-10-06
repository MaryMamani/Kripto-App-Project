package com.marymamani.kriptoapp.domain.usecase

import com.marymamani.kriptoapp.data.model.AppResourceModel
import com.marymamani.kriptoapp.domain.model.DetailResourceUsageApp
import com.marymamani.kriptoapp.domain.model.TopResourceUsageApps
import com.marymamani.kriptoapp.domain.model.TopResourcesUsageAllApps
import com.marymamani.kriptoapp.domain.repository.AppRepository
import com.marymamani.kriptoapp.utils.NumberUtils

class GetTopResourcesUsageAppsUseCase(private val appRepository: AppRepository) {
    suspend operator fun invoke(numberTop: Int): TopResourcesUsageAllApps {
        val appResources = appRepository.getAllAppResource()

        val topMemoryUsage = analyzeMemoryUsage(appResources, numberTop)

        val topCpuUsage = analyzeCpuUsage(appResources, numberTop)

        val topMobileDataUsage = analyzeMobileDataUsage(appResources, numberTop)

        val topWifiDataUsage = analyzeWifiDataUsage(appResources, numberTop)

        return TopResourcesUsageAllApps(
            topMemoryUsage = topMemoryUsage,
            topCpuUsage = topCpuUsage,
            topMobileDataUsage = topMobileDataUsage,
            topWifiDataUsage = topWifiDataUsage
        )
    }

    private fun analyzeMemoryUsage(appResources: List<AppResourceModel>, numberTop: Int): TopResourceUsageApps {
        val totalMemoryUsage = appResources.sumOf { it.ramUsageMB }
        val topApps = getTopApps(appResources, totalMemoryUsage, numberTop) { it.ramUsageMB }
        val otherApps = getOtherAppsUsage(appResources, topApps, totalMemoryUsage) { it.ramUsageMB }

        return TopResourceUsageApps(totalMemoryUsage, topApps + listOfNotNull(otherApps))
    }

    private fun analyzeCpuUsage(appResources: List<AppResourceModel>, numberTop: Int): TopResourceUsageApps {
        val totalCpuUsage = appResources.sumOf { it.cpuUsagePercentage.toInt() }
        val topApps = getTopApps(appResources, totalCpuUsage, numberTop) { it.cpuUsagePercentage.toInt() }
        val otherApps = getOtherAppsUsage(appResources, topApps, totalCpuUsage) { it.cpuUsagePercentage.toInt() }

        return TopResourceUsageApps(totalCpuUsage, topApps + listOfNotNull(otherApps))
    }

    private fun analyzeMobileDataUsage(appResources: List<AppResourceModel>, numberTop: Int): TopResourceUsageApps {
        val totalMobileDataUsage = appResources.sumOf { it.mobileDataUsageMB }
        val topApps = getTopApps(appResources, totalMobileDataUsage.toInt(), numberTop) { it.mobileDataUsageMB }
        val otherApps = getOtherAppsUsage(appResources, topApps, totalMobileDataUsage.toInt()) { it.mobileDataUsageMB }

        return TopResourceUsageApps(totalMobileDataUsage.toInt(), topApps + listOfNotNull(otherApps))
    }

    private fun analyzeWifiDataUsage(appResources: List<AppResourceModel>, numberTop: Int): TopResourceUsageApps {
        val totalWifiDataUsage = appResources.sumOf { it.wifiDataUsageMB }
        val topApps = getTopApps(appResources, totalWifiDataUsage.toInt(), numberTop) { it.wifiDataUsageMB }
        val otherApps = getOtherAppsUsage(appResources, topApps, totalWifiDataUsage.toInt()) { it.wifiDataUsageMB }

        return TopResourceUsageApps(totalWifiDataUsage.toInt(), topApps + listOfNotNull(otherApps))
    }

    private fun <T : Number> getTopApps(appResources: List<AppResourceModel>, totalUsage: Int, numberTop: Int, usageSelector: (AppResourceModel) -> T): List<DetailResourceUsageApp> {
        return appResources
            .sortedByDescending { usageSelector(it).toDouble() }
            .take(numberTop)
            .map { app ->
                DetailResourceUsageApp(
                    appName = app.appName,
                    percentage = NumberUtils.calculatePercentage(usageSelector(app).toInt(), totalUsage),
                    usage = usageSelector(app).toFloat(),
                    icon = app.icon
                )
            }
    }

    private fun <T : Number> getOtherAppsUsage(
        appResources: List<AppResourceModel>,
        topApps: List<DetailResourceUsageApp>,
        totalUsage: Int,
        usageSelector: (AppResourceModel) -> T
    ): DetailResourceUsageApp? {
        val otherAppsUsage = appResources
            .filter { app -> topApps.none { it.appName == app.appName } }
            .sumOf { usageSelector(it).toDouble() }

        return if (otherAppsUsage > 0) {
            DetailResourceUsageApp(
                appName = "Otros",
                percentage = NumberUtils.calculatePercentage(otherAppsUsage.toInt(), totalUsage),
                usage = otherAppsUsage.toFloat(),
                icon = null
            )
        } else {
            null
        }
    }
}
