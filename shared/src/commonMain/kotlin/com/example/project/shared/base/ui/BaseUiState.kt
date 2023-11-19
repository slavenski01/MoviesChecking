package com.example.project.shared.base.ui

sealed interface BaseUiState<out T> {
    data class Success<T>(val data: T) : BaseUiState<T>
    data class Error(val message: String? = null) : BaseUiState<Nothing>
    data object Loading : BaseUiState<Nothing>
    data object Empty : BaseUiState<Nothing>
    data object Idle : BaseUiState<Nothing>
}