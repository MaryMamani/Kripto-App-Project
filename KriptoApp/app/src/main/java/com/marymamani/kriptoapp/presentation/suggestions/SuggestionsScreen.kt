package com.marymamani.kriptoapp.presentation.suggestions

import android.graphics.drawable.Drawable
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.marymamani.kriptoapp.R
import com.marymamani.kriptoapp.domain.model.TopResourceUsageApps
import com.marymamani.kriptoapp.domain.model.TopResourcesUsageAllApps
import com.marymamani.kriptoapp.ui.widget.ProgressLoading
import com.marymamani.kriptoapp.utils.ResultState

@Composable
fun SuggestionsScreen(viewModel: SuggestionViewModel = hiltViewModel()) {
    var expandedIndex by remember { mutableStateOf(0) }
    val suggestionsState by viewModel.topResourcesUsageAppsState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.suggestions_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (suggestionsState) {
            is ResultState.Loading -> {
                ProgressLoading()
            }
            is ResultState.Success -> {
                val data = (suggestionsState as ResultState.Success<TopResourcesUsageAllApps>).data

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    val suggestions = SuggestionType.values().toList()

                    itemsIndexed(suggestions) { index, suggestionType ->
                        val details = when (suggestionType) {
                            SuggestionType.MEMORY -> getDetailForResource(data!!.topMemoryUsage)
                            SuggestionType.CPU -> getDetailForResource(data!!.topCpuUsage)
                            SuggestionType.MOBILE_DATA -> getDetailForResource(data!!.topMobileDataUsage)
                            SuggestionType.WIFI -> getDetailForResource(data!!.topWifiDataUsage)
                        }

                        SuggestionCard(
                            suggestionType = suggestionType,
                            details = details,
                            isExpanded = expandedIndex == index,
                            onCardClick = {
                                expandedIndex = if (expandedIndex == index) -1 else index
                            }
                        )
                    }
                }
            }

            is ResultState.Error -> {
                val errorMessage = (suggestionsState as ResultState.Error).message
                Text(errorMessage ?: "")
            }
        }
    }
}

fun getDetailForResource(resource: TopResourceUsageApps): List<Pair<Drawable?, String>> {
    return resource.appList.map { app ->
        app.icon to "${app.appName}: ${app.percentage}% (${app.usage} MB)"
    }
}



@Composable
fun SuggestionCard(suggestionType: SuggestionType, details: List<Pair<Drawable?, String>>, isExpanded: Boolean, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { onCardClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.flash_ic),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = suggestionType.titleRes),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            if (isExpanded) {
                details.forEach { (icon, detailText) ->
                    icon?.let {
                        Text(
                            text = stringResource(id = suggestionType.messageRes),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                rememberAsyncImagePainter(icon),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = detailText,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Button(
                    onClick = { onCardClick() },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = stringResource(R.string.apply_button), fontSize = 12.sp)
                }
            }
        }
    }
}

enum class SuggestionType(@StringRes val titleRes: Int, @StringRes val messageRes: Int) {
    MEMORY(R.string.suggestion_memory_title, R.string.suggestion_memory_message),
    CPU(R.string.suggestion_cpu_title, R.string.suggestion_cpu_message),
    MOBILE_DATA(R.string.suggestion_mobile_data_title, R.string.suggestion_mobile_data_message),
    WIFI(R.string.suggestion_wifi_title, R.string.suggestion_wifi_message)
}

