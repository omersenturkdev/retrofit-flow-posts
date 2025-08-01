package com.omersenturk.apiflowposts.di

import com.omersenturk.apiflowposts.data.remote.PostApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun providePostApi(retrofit: Retrofit): PostApi = retrofit.create(PostApi::class.java)
}