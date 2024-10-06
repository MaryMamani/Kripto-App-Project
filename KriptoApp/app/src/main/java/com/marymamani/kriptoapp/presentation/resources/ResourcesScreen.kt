package com.marymamani.kriptoapp.presentation.resources

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.marymamani.kriptoapp.R
import com.marymamani.kriptoapp.domain.model.DetailResourceUsageApp
import com.marymamani.kriptoapp.ui.widget.ProgressLoading
import com.marymamani.kriptoapp.utils.ResultState

@Composable
fun ResourcesScreen() {
    val viewModel: ResourcesViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.fetchTopMemoryUsageApps()
    }

    val resourcesState by viewModel.topResourcesUsageAppsState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        when (resourcesState) {
            is ResultState.Loading -> {
                item {
                    ProgressLoading()
                }
            }
            is ResultState.Error -> {
                (resourcesState as ResultState.Error).message?.let {
                    item {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            is ResultState.Success -> {
                val topResources = (resourcesState as ResultState.Success).data

                item {
                    if (topResources != null) {
                        ResourceUsageCard(
                            title = stringResource(R.string.resource_memory_title),
                            totalUsage = topResources.topMemoryUsage.totalUsage,
                            resourceUsageApps = topResources.topMemoryUsage.appList
                        )
                    }
                }

                item {
                    if (topResources != null) {
                        ResourceUsageCard(
                            title = stringResource(R.string.resource_cpu_title),
                            totalUsage = topResources.topCpuUsage.totalUsage,
                            resourceUsageApps = topResources.topCpuUsage.appList
                        )
                    }
                }

                item {
                    if (topResources != null) {
                        ResourceUsageCard(
                            title = stringResource(R.string.resource_mobile_data_title),
                            totalUsage = topResources.topMobileDataUsage.totalUsage,
                            resourceUsageApps = topResources.topMobileDataUsage.appList
                        )
                    }
                }

                item {
                    if (topResources != null) {
                        ResourceUsageCard(
                            title = stringResource(R.string.resource_wifi_title),
                            totalUsage = topResources.topWifiDataUsage.totalUsage,
                            resourceUsageApps = topResources.topWifiDataUsage.appList
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun ResourceUsageCard(
    title: String,
    totalUsage: Int,
    resourceUsageApps: List<DetailResourceUsageApp>
) {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.error,
        MaterialTheme.colorScheme.errorContainer,
    )
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(8.dp))
    Card(
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            resourceUsageApps.forEachIndexed { index, app ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (app.icon != null) {
                        Image(
                            rememberAsyncImagePainter(app.icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Icon(
                            painterResource(id = R.drawable.apps_ic),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = app.appName,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        val progress = app.usage / totalUsage.toFloat()

                        LinearProgressIndicator(
                            progress = progress,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            color = colors.getOrElse(index) { Color.Gray }
                        )

                        Text(
                            text = "${app.percentage}% utilizado",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
