package com.omersenturk.apiflowposts.data.remote

import com.omersenturk.apiflowposts.data.PostResponse
import retrofit2.http.GET

interface PostApi {
    @GET("posts")
    suspend fun getPosts() : List<PostResponse>
}