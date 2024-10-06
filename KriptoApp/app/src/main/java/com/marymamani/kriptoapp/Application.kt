package com.marymamani.kriptoapp

import android.app.Application
import com.marymamani.kriptoapp.presentation.apps.AppsViewModel
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()

    }

}