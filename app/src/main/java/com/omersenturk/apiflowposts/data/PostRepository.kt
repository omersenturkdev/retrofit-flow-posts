package com.omersenturk.apiflowposts.data

import com.omersenturk.apiflowposts.data.models.PostResponse
import com.omersenturk.apiflowposts.data.remote.PostApi
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val api: PostApi
) {
    suspend fun getPost(): Result<List<PostResponse>> {
        return try {
            val response = api.getPosts()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun searchPosts(title: String): Result<List<PostResponse>> {
        return try {
            val response = api.searchPosts(title)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}