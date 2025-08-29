package com.omersenturk.apiflowposts.data.remote

import com.omersenturk.apiflowposts.data.models.PostResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.Response

interface PostApi {
    @GET("posts")
    suspend fun getPosts() : List<PostResponse>

    @GET("posts")
    suspend fun searchPosts(@Query("title_like") title: String): List<PostResponse>

    @POST("posts")
    suspend fun createPost(@Body post: Map<String, Any>): Response<PostResponse>

}