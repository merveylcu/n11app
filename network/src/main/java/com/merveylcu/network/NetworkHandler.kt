package com.merveylcu.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Response

object NetworkHandler {

    private var onNetworkError: (() -> Unit)? = null
    private var onNetworkConnectivityError: (() -> String)? = null
    private var showLoading: (() -> Unit)? = null
    private var hideLoading: (() -> Unit)? = null
    private var onError: ((String) -> Unit)? = null

    fun setNetworkActions(
        onNetworkError: (() -> Unit)?,
        onNetworkConnectivityError: (() -> String)?,
        showLoading: (() -> Unit)?,
        hideLoading: (() -> Unit)?,
        onError: ((String) -> Unit)?
    ) {
        this.onNetworkError = onNetworkError
        this.onNetworkConnectivityError = onNetworkConnectivityError
        this.showLoading = showLoading
        this.hideLoading = hideLoading
        this.onError = onError
    }

    suspend fun <T> sendRequest(
        context: Context? = null,
        request: suspend () -> Response<T>,
        isAsync: Boolean = false,
        isShowErrorDialog: Boolean = true,
        loadingDelay: Long? = null
    ): AppResult<T> {

        if (context != null && NetworkManager.isNetworkAvailable(context).not()) {
            return noNetworkConnectivityError()
        }

        return execute(request, isAsync, isShowErrorDialog, loadingDelay)
    }

    private suspend fun <T> execute(
        request: suspend () -> Response<T>,
        isAsync: Boolean = false,
        isShowErrorDialog: Boolean = true,
        loadingDelay: Long? = null
    ): AppResult<T> {

        var result: AppResult<T>? = null
        lateinit var response: Response<T>

        val loadingDelayHandler = Handler(Looper.getMainLooper())
        showLoading(isAsync, loadingDelay, loadingDelayHandler)
        val job = CoroutineScope(Dispatchers.IO).async {
            try {
                response = request.invoke()
            } catch (e: Exception) {
                result = AppResult.Error(e)
            }
        }
        job.await()
        hideLoading(isAsync, loadingDelayHandler)
        if (checkNetworkError(result)) return result!!
        result = getResult(response, isShowErrorDialog)
        return result!!
    }

    private fun <T> checkNetworkError(result: AppResult<T>?): Boolean {
        if (result != null && result is AppResult.Error) {
            onNetworkError?.invoke()
            return true
        }
        return false
    }

    private fun <T> getResult(response: Response<T>, isShowErrorDialog: Boolean): AppResult<T> {
        return when (response.code()) {
            200 -> {
                handleSuccess(response, isShowErrorDialog)
            }
            else -> {
                handleApiError(response, isShowErrorDialog)
            }
        }
    }

    private fun noNetworkConnectivityError(): AppResult.Error {
        val message = onNetworkConnectivityError?.invoke()
        return AppResult.Error(Exception(message))
    }

    private fun showLoading(isAsync: Boolean, loadingDelay: Long?, loadingDelayHandler: Handler) {
        if (isAsync) return

        if (loadingDelay == null) {
            showLoading?.invoke()
        } else {
            loadingDelayHandler.postDelayed({
                showLoading?.invoke()
            }, loadingDelay)
        }
    }

    private fun hideLoading(isAsync: Boolean, loadingDelayHandler: Handler) {
        loadingDelayHandler.removeCallbacksAndMessages(null)

        if (!isAsync) {
            hideLoading?.invoke()
        }
    }

    private fun <T> handleApiError(
        response: Response<T>,
        isShowErrorDialog: Boolean
    ): AppResult<Nothing> {
        val message = ApiErrorUtils.parseError(response)
        if (isShowErrorDialog) {
            onError?.invoke(message)
        }
        return AppResult.Error(Exception(message))
    }

    private fun <T> handleSuccess(response: Response<T>, isShowErrorDialog: Boolean): AppResult<T> {
        response.body()?.let {
            return AppResult.Success(it)
        } ?: return handleApiError(response, isShowErrorDialog)
    }

}
