package com.merveylcu.network

sealed class AppResult<out T> {
    data class Success<out T>(val successData: T?) : AppResult<T>()
    class Error(
        private val exception: Exception,
        val message: String = exception.localizedMessage ?: ""
    ) : AppResult<Nothing>()
}