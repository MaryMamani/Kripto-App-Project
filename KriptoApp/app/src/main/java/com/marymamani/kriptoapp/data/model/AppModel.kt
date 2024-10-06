package com.marymamani.kriptoapp.data.model

import android.graphics.drawable.Drawable

data class AppModel(
    val id: String,
    val uid: Int,
    val packageName: String,
    val appName: String,
    val version: String,
    val isSystemApp: Boolean,
    val installDate: Long? = null,
    val lastUpdateDate: Long,
    var icon: Drawable? = null
)
