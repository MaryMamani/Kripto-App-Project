package com.marymamani.kriptoapp.presentation.apps

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.marymamani.kriptoapp.R
import com.marymamani.kriptoapp.domain.model.App
import com.marymamani.kriptoapp.ui.widget.ProgressLoading
import com.marymamani.kriptoapp.utils.DateUtils
import com.marymamani.kriptoapp.utils.ResultState

@Composable
fun AppsScreen() {

    val viewModel: AppsViewModel = hiltViewModel()

    val appsState by viewModel.appsState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (appsState) {
            is ResultState.Loading -> {
                item {
                    ProgressLoading()
                }
            }
            is ResultState.Success -> {
                val apps = (appsState as ResultState.Success<List<App>>).data ?: emptyList()
                val systemApps = apps.filter { it.isSystemApp }
                val thirdPartyApps = apps.filter { !it.isSystemApp }

                if (thirdPartyApps.isNotEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.apps_tertiary_title),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                        )
                    }
                    items(thirdPartyApps) { app ->
                        AppItem(
                            appName = app.appName,
                            appIcon = app.icon,
                            lastUpdate = app.lastUpdate
                        )
                    }
                }

                if (systemApps.isNotEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.apps_system_title),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                        )
                    }
                    items(systemApps) { app ->
                        AppItem(
                            appName = app.appName,
                            appIcon = app.icon,
                            lastUpdate = app.lastUpdate
                        )
                    }
                }
            }
            is ResultState.Error -> {
                item {
                    val errorMessage = (appsState as ResultState.Error).message
                    Text(errorMessage?: "")
                }
            }
        }
    }
}


@Composable
fun AppItem(appName: String, lastUpdate: Long?, appIcon: Drawable?) {
    val formattedDate = DateUtils.formatTimestamp(lastUpdate)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            appIcon?.let {
                Image(
                    painter = rememberAsyncImagePainter(appIcon),
                    contentDescription = stringResource(R.string.app_icon_description),
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = appName, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = stringResource(R.string.apps_last_update, formattedDate),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
