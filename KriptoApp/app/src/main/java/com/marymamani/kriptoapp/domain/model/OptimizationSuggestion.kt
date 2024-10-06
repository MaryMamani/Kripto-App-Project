package com.marymamani.kriptoapp.domain.model

import com.marymamani.kriptoapp.data.model.SuggestionType

data class OptimizationSuggestion(
    val id: String,
    val appId: String,
    val suggestionType: SuggestionType,
    val description: String,
    val priority: Int,
    val dateSuggested: Long
)
