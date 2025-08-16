package com.omersenturk.apiflowposts.data.remote

import com.omersenturk.apiflowposts.data.models.PostResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {
    @GET("posts")
    suspend fun getPosts() : List<PostResponse>

    @GET("posts")
    suspend fun searchPosts(@Query("title") title: String): List<PostResponse>

}