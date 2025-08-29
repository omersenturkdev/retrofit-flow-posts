package com.omersenturk.apiflowposts.ui

import com.omersenturk.apiflowposts.data.models.PostUiModel

sealed class PostState {
    object Loading : PostState()
    data class Success(val posts :List<PostUiModel>) : PostState()
    data class Error(val message : String) : PostState()
}