package com.marymamani.kriptoapp.data.datasource.system

import android.app.ActivityManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import com.marymamani.kriptoapp.data.model.AppModel
import com.marymamani.kriptoapp.data.model.AppResourceModel
import com.marymamani.kriptoapp.utils.NumberUtils

class AppSystemDataSourceImpl(
    private val context: Context,
    private val usageStatsManager: UsageStatsManager
): AppSystemDataSource {

    override fun getAllApp(): List<AppModel> {
        val packageManager = context.packageManager
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        return packages
            .filter {applicationInfo ->
                val isSystemApp = (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                val isUpdatedSystemApp = (applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0
                val isInstalledApp = (applicationInfo.flags and ApplicationInfo.FLAG_INSTALLED) != 0

                !isSystemApp || (isUpdatedSystemApp && isInstalledApp)
        }
            .map { appInfo ->
            with(packageManager) {
                val appName = getApplicationLabel(appInfo).toString()
                val uid = appInfo.uid
                val packageName = appInfo.packageName
                val version = getPackageInfo(packageName, 0).versionName
                val isSystemApp = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                val installDate = getPackageInfo(packageName, 0).firstInstallTime
                val lastUpdateDate = getPackageInfo(packageName, 0).lastUpdateTime
                val icon = getApplicationIcon(appInfo)

                AppModel(
                    id = packageName,
                    uid = uid,
                    packageName = packageName,
                    appName = appName,
                    version = version ?: "N/A",
                    isSystemApp = isSystemApp,
                    installDate = installDate,
                    lastUpdateDate = lastUpdateDate,
                    icon = icon
                )
            }
        }
    }

    override fun getAppResourceById(appModel: AppModel): AppResourceModel {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcessInfo = activityManager.runningAppProcesses

        runningAppProcessInfo.forEach { process ->
            process.uid
            process.importance
        }
        return AppResourceModel(
            appId = appModel.id,
            mobileDataUsageMB = getAppDataUsage(appModel.packageName, ConnectivityManager.TYPE_MOBILE),
            wifiDataUsageMB = getAppDataUsage(appModel.packageName, ConnectivityManager.TYPE_WIFI),
            cpuUsagePercentage = getCpuUsage(appModel.packageName),
            ramUsageMB = getMemoryUsage(appModel.packageName),
            lastOpenedTimestamp = getLastOpenedByPackageName(appModel.packageName),
            packageName = appModel.packageName,
            appName = appModel.appName
        )
    }

    override fun getIconAppByPackageName(packageName: String): Drawable {
        val packageManager = context.packageManager
        return packageManager.getApplicationIcon(packageName)
    }

    private fun getLastOpenedByPackageName(packageName: String): Long {
        val endTime = System.currentTimeMillis()
        val startTime = endTime - (1000 * 60 * 60 * 24 * 7)

        val usageStatsList = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        val appUsageStats = usageStatsList.find { it.packageName == packageName }

        return NumberUtils.getRandomValue(0).toLong()
    }

    private fun getAppDataUsage(packageName: String, typeConnectivity: Int): Long {
        /*val networkStatsManager = context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager

        val networkStats = networkStatsManager.querySummary(
            typeConnectivity,
            "",
            0,
            System.currentTimeMillis()
        )

        var totalBytes = 0L

        while (networkStats.hasNextBucket()) {
            val bucket = NetworkStats.Bucket()
            networkStats.getNextBucket(bucket)

            if (bucket.getUid() == context.packageManager.getApplicationInfo(packageName, 0).uid) {
                totalBytes += bucket.getRxBytes() + bucket.getTxBytes() // Suma el uso de datos recibidos y transmitidos
            }
        }*/

        return NumberUtils.getRandomValue(0).toLong()
    }

    private fun getCpuUsage(packageName: String): Float {
        /*val process = Runtime.getRuntime().exec("top -n 1")
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            if (line!!.contains(packageName)) {
                val tokens = line!!.trim().split("\\s+".toRegex())
                return tokens[1].toFloat()
            }
        }*/
        return NumberUtils.getRandomValue(0).toFloat()
    }

    private fun getMemoryUsage(packageName: String): Int {
        /*val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses

        for (processInfo in runningAppProcesses) {
            if (processInfo.processName == packageName) {
                return processInfo.importanceReasonCode
            }
        }*/
        return NumberUtils.getRandomValue(0)
    }
}