package com.merveylcu.n11app.data.model.search

data class SearchUserResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<User>
)