package com.merveylcu.n11app.service.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.merveylcu.n11app.R
import com.merveylcu.n11app.core.Constants
import com.merveylcu.n11app.util.extensions.getStr
import com.merveylcu.n11app.util.extensions.showDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Response

object NetworkHandler {

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
            showNetworkErrorDialog()
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
        val errorTitle = Constants.App.latestActivity!!.getStr(R.string.error)
        val errorMessage =
            Constants.App.latestActivity?.getStr(R.string.no_network_connectivity)
                ?: ""

        Constants.App.latestActivity?.showDialog(
            title = errorTitle,
            message = errorMessage,
            positiveButtonText = Constants.App.latestActivity!!.getStr(R.string.ok)
        )

        return AppResult.Error(Exception(errorMessage))
    }

    private fun showLoading(isAsync: Boolean, loadingDelay: Long?, loadingDelayHandler: Handler) {
        if (isAsync) return

        if (loadingDelay == null) {
            Constants.App.latestActivity?.showLoading()
        } else {
            loadingDelayHandler.postDelayed({
                Constants.App.latestActivity?.showLoading()
            }, loadingDelay)
        }
    }

    private fun hideLoading(isAsync: Boolean, loadingDelayHandler: Handler) {
        loadingDelayHandler.removeCallbacksAndMessages(null)

        if (!isAsync) {
            Constants.App.latestActivity?.hideLoading()
        }
    }

    private fun showNetworkErrorDialog() {
        Constants.App.latestActivity?.showDialog(
            title = Constants.App.latestActivity!!.getStr(R.string.error),
            message = Constants.App.latestActivity!!.getStr(R.string.error_occurred),
            positiveButtonText = Constants.App.latestActivity!!.getStr(R.string.ok)
        )
    }

    private fun showErrorDialog(
        title: String,
        message: String,
        positiveButtonText: String,
        positiveButtonAction: (() -> Unit)? = null
    ) {
        Constants.App.latestActivity?.showDialog(
            title = title,
            message = message,
            positiveButtonText = positiveButtonText,
            positiveButtonAction = positiveButtonAction
        )
    }

    private fun <T> handleApiError(
        response: Response<T>,
        isShowErrorDialog: Boolean
    ): AppResult<Nothing> {

        val title = Constants.App.latestActivity!!.getStr(R.string.error)
        var message = Constants.App.latestActivity!!.getStr(R.string.error_occurred)
        val error = ApiErrorUtils.parseError(response)

        try {
            message = error.message ?: ""
        } catch (e: Exception) {
        }

        if (isShowErrorDialog) {
            showErrorDialog(
                title = title,
                message = message,
                positiveButtonText = Constants.App.latestActivity!!.getStr(R.string.ok)
            )
        }

        return AppResult.Error(Exception(message))
    }

    private fun <T> handleSuccess(response: Response<T>, isShowErrorDialog: Boolean): AppResult<T> {
        response.body()?.let {
            return AppResult.Success(it)
        } ?: return handleApiError(response, isShowErrorDialog)
    }

}
