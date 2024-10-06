package com.marymamani.kriptoapp.navigation

import com.marymamani.kriptoapp.R

enum class Screen {
    APPS,
    RESOURCES,
    SUGGESTIONS
}
sealed class NavigationItem(val route: String, val title: String, val icon: Int) {
    object Apps : NavigationItem(Screen.APPS.name, "Apps", R.drawable.apps_ic)
    object Resources : NavigationItem(Screen.RESOURCES.name, "Recursos", R.drawable.storage_ic)
    object Suggestions : NavigationItem(Screen.SUGGESTIONS.name, "Sugerencias", R.drawable.lightbulb_ic)
}