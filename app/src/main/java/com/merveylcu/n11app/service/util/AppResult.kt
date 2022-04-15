package com.merveylcu.n11app.service.util

sealed class AppResult<out T> {
    data class Success<out T>(val successData: T?) : AppResult<T>()
    class Error(
        private val exception: Exception,
        val message: String = exception.localizedMessage ?: ""
    ) : AppResult<Nothing>()
}