package com.example.myapplicationretrofot2

import retrofit2.Response
import retrofit2.http.GET

interface CommentApi {
    @GET("/comments")
    suspend fun getComments(): Response<List<Comment>>

}