package com.omersenturk.apiflowposts.data.models

import androidx.annotation.Keep

@Keep
data class PostResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)