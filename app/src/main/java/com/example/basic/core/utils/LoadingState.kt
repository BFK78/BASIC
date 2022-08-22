package com.example.basic.core.utils

data class LoadingState(val isLoading: Boolean?, val message: String? = null) {
    companion object {
        val LOADING = LoadingState(isLoading = true)
        val DONE = LoadingState(isLoading = false)
        fun error(message: String): LoadingState = LoadingState(isLoading = false, message = message)
    }
}
