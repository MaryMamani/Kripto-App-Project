package com.marymamani.kriptoapp.domain.model

import android.graphics.drawable.Drawable

data class App(
    val id: String,
    val appName: String,
    val isSystemApp: Boolean,
    val lastUpdate: Long? = null,
    val icon: Drawable? = null
)