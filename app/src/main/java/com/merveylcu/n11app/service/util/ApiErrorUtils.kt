package com.merveylcu.n11app.service.util

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException

object ApiErrorUtils {

    fun parseError(response: Response<*>): APIError {
        val gson = GsonBuilder().create()
        val error: APIErrorResponse
        try {
            error = gson.fromJson(response.errorBody()?.string(), APIErrorResponse::class.java)
        } catch (e: IOException) {
            e.message?.let { Log.d("exception", it) }
            return APIError()
        }
        return error.error
    }

}