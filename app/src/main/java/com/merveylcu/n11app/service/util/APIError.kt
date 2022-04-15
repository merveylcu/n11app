package com.merveylcu.n11app.service.util

data class APIError(val errorCode: String? = "", val message: String? = "") {
    constructor() : this("")
}

data class APIErrorResponse(val success: Boolean, val error: APIError)