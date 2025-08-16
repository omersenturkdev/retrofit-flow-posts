package com.omersenturk.apiflowposts.data.models

import androidx.annotation.Keep

@Keep
data class PostUiModel(
    val title: String,
    val body: String,
    val image: String,
)