package com.marymamani.kriptoapp.data.model

data class OptimizationSuggestionModel(
    val app: AppModel,
    val suggestionType: SuggestionType,
    val description: String,
    val priority: Int,
    val dateSuggested: Long
)

enum class SuggestionType {
    CLEAR_CACHE, UNINSTALL_APP, OPTIMIZE_RESOURCES
}