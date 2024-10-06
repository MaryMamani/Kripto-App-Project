package com.marymamani.kriptoapp.domain.model

import android.graphics.drawable.Drawable

data class DetailResourceUsageApp(
    val appName: String,
    val percentage: Float,
    val usage: Float,
    val icon: Drawable?
)
