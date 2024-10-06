package com.marymamani.kriptoapp.di

import android.app.Application
import android.app.usage.UsageStatsManager
import android.content.Context
import androidx.room.Room
import com.marymamani.kriptoapp.data.datasource.local.AppLocalDataSource
import com.marymamani.kriptoapp.data.datasource.local.AppLocalDataSourceImpl
import com.marymamani.kriptoapp.data.datasource.local.db.AppDao
import com.marymamani.kriptoapp.data.datasource.local.db.AppDatabase
import com.marymamani.kriptoapp.data.datasource.local.db.AppResourceDao
import com.marymamani.kriptoapp.data.datasource.system.AppSystemDataSource
import com.marymamani.kriptoapp.data.datasource.system.AppSystemDataSourceImpl
import com.marymamani.kriptoapp.data.repository.AppRepositoryImpl
import com.marymamani.kriptoapp.domain.repository.AppRepository
import com.marymamani.kriptoapp.domain.usecase.GetAllAppsResourceUseCase
import com.marymamani.kriptoapp.domain.usecase.GetAllAppsUseCase
import com.marymamani.kriptoapp.domain.usecase.GetTopResourcesUsageAppsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppLocalDataSource(appDao: AppDao, appResourceDao: AppResourceDao): AppLocalDataSource {
        return AppLocalDataSourceImpl(appDao, appResourceDao)
    }

    @Provides
    @Singleton
    fun provideAppSystemDataSource(@ApplicationContext context: Context, usageStatsManager: UsageStatsManager,): AppSystemDataSource {
        return AppSystemDataSourceImpl(context, usageStatsManager)
    }

    @Provides
    @Singleton
    fun provideUsageStatsManager(application: Application): UsageStatsManager {
        return application.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        appLocalDataSource: AppLocalDataSource,
        appSystemDataSource: AppSystemDataSource
    ): AppRepository {
        return AppRepositoryImpl(appLocalDataSource, appSystemDataSource)
    }

    @Provides
    @Singleton
    fun provideGetAllAppsUseCase(appRepository: AppRepository): GetAllAppsUseCase {
        return GetAllAppsUseCase(appRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllAppsResourceUseCase(appRepository: AppRepository): GetAllAppsResourceUseCase {
        return GetAllAppsResourceUseCase(appRepository)
    }

    @Provides
    @Singleton
    fun provideAppDao(appDatabase: AppDatabase): AppDao {
        return appDatabase.appDao()
    }

    @Provides
    @Singleton
    fun provideAppResourceDao(appDatabase: AppDatabase): AppResourceDao {
        return appDatabase.appResourceDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGetTopMemoryUsageAppUseCase(appRepository: AppRepository): GetTopResourcesUsageAppsUseCase {
        return GetTopResourcesUsageAppsUseCase(appRepository)
    }
}