package com.s005.fif.chat.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Streaming

interface ChatService {
    @Streaming
    @GET("generate-AI")
    suspend fun getChatResponse(
        @Query("ingredients") ingredients: String,
        @Query("diseases") diseases: String,
        @Query("dislikeIngredients") dislikeIngredients: String,
        @Query("prompt") prompt: String
    ) : Response<ResponseBody>
}