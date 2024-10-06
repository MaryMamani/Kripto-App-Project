package com.marymamani.kriptoapp.presentation.resources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marymamani.kriptoapp.domain.model.TopResourcesUsageAllApps
import com.marymamani.kriptoapp.domain.usecase.GetTopResourcesUsageAppsUseCase
import com.marymamani.kriptoapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourcesViewModel @Inject constructor(
    private val getTopResourcesUsageAppsUseCase: GetTopResourcesUsageAppsUseCase
) : ViewModel() {

    private val _topResourcesUsageAppsState = MutableStateFlow<ResultState<TopResourcesUsageAllApps>>(ResultState.Loading())
    val topResourcesUsageAppsState: StateFlow<ResultState<TopResourcesUsageAllApps>> = _topResourcesUsageAppsState

     fun fetchTopMemoryUsageApps() {
        viewModelScope.launch {
            try {
                _topResourcesUsageAppsState.value = ResultState.Loading()
                val topMemoryUsageApps = getTopResourcesUsageAppsUseCase(5)
                _topResourcesUsageAppsState.value = ResultState.Success(topMemoryUsageApps)
            } catch (e: Exception) {
                _topResourcesUsageAppsState.value = ResultState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}