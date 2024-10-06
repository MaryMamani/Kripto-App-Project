package com.marymamani.kriptoapp.presentation.apps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marymamani.kriptoapp.domain.model.App
import com.marymamani.kriptoapp.domain.usecase.GetAllAppsUseCase
import com.marymamani.kriptoapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsViewModel  @Inject constructor(
    private val getAllAppsUseCase: GetAllAppsUseCase
) : ViewModel() {

    private val _appsState = MutableStateFlow<ResultState<List<App>>>(ResultState.Loading())
    val appsState: StateFlow<ResultState<List<App>>> = _appsState

    init {
        fetchAllApps()
    }

    private fun fetchAllApps() {
        viewModelScope.launch {
            try {
                _appsState.value = ResultState.Loading()
                val appList = getAllAppsUseCase()
                _appsState.value = ResultState.Success(appList)
            } catch ( e: Exception ) {
                println(e.message)
                println(e.stackTrace)
                _appsState.value = ResultState.Error("Error al cargar aplicaciones", null)
            }
        }
    }

}
