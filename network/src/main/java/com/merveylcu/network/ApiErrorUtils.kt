package com.merveylcu.network

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException

object ApiErrorUtils {

    fun parseError(response: Response<*>): String {
        val gson = GsonBuilder().create()
        val apiErrorResponse: APIErrorResponse
        try {
            apiErrorResponse = gson.fromJson(response.errorBody()?.string(), APIErrorResponse::class.java)
        } catch (e: IOException) {
            e.message?.let { Log.d("exception", it) }
            return e.message ?: ""
        }
        return apiErrorResponse.message
    }

}