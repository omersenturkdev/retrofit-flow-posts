package com.omersenturk.apiflowposts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersenturk.apiflowposts.data.PostRepository
import com.omersenturk.apiflowposts.data.models.PostResponse
import com.omersenturk.apiflowposts.data.models.PostUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<Result<List<PostUiModel>>?>(null)
    val posts: StateFlow<Result<List<PostUiModel>>?> = _posts

    fun fetchPosts() {
        viewModelScope.launch {
            val result = repository.getPost()
            _posts.value = result.map { posts -> mapToUiModel(posts) }
        }
    }

    fun searchPosts(query: String) {
        viewModelScope.launch {
            val result = repository.searchPosts(query)
            _posts.value = result.map { posts -> mapToUiModel(posts) }
        }
    }

    private fun mapToUiModel(posts: List<PostResponse>): List<PostUiModel> {
        return posts.map { post ->
            PostUiModel(
                title = post.title,
                body = post.body,
                image = "https://picsum.photos/200/300?random=${post.id}"
            )
        }
    }
}
