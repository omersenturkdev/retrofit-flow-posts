package com.omersenturk.apiflowposts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersenturk.apiflowposts.data.PostRepository
import com.omersenturk.apiflowposts.data.models.PostResponse
import com.omersenturk.apiflowposts.data.models.PostUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<PostState>(PostState.Loading)
    val posts: StateFlow<PostState> = _posts

    private var job: Job? = null

    fun fetchPosts() {
        viewModelScope.launch {
            _posts.value = PostState.Loading
            try {
                val result = repository.getPost()
                result.onSuccess { posts ->
                    _posts.value = PostState.Success(mapToUiModel(posts))
                }.onFailure { e ->
                    _posts.value = PostState.Error(e.message ?: "Error")
                }
            } catch (e: Exception) {
                _posts.value = PostState.Error(e.message ?: "Error")
            }
        }
    }

    fun searchPosts(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            _posts.value = PostState.Loading
            try {
                val result = repository.searchPosts(query)
                result.onSuccess { posts ->
                    _posts.value = PostState.Success(mapToUiModel(posts))
                }.onFailure { e ->
                    _posts.value = PostState.Error(e.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _posts.value = PostState.Error(e.message ?: "Unknown error")
            }
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
