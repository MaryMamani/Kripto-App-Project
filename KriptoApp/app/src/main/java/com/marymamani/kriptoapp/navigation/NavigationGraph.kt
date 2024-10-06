package com.marymamani.kriptoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.marymamani.kriptoapp.presentation.apps.AppsScreen
import com.marymamani.kriptoapp.presentation.resources.ResourcesScreen
import com.marymamani.kriptoapp.presentation.suggestions.SuggestionsScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController,
        startDestination = NavigationItem.Apps.route,
        modifier = modifier
    ) {
        composable(NavigationItem.Apps.route) {
            AppsScreen()
        }
        composable(NavigationItem.Resources.route) {
            ResourcesScreen()
        }
        composable(NavigationItem.Suggestions.route) {
            SuggestionsScreen()
        }
    }
}