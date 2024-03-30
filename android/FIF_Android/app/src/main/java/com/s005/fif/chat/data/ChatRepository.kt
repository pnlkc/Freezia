package com.s005.fif.chat.data

import com.s005.fif.chat.network.ChatService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface ChatRepository {
    suspend fun getChatResponse(
        ingredients: String,
        diseases: String,
        dislikeIngredients: String,
        prompt: String
    ) : Flow<String?>
}

class DefaultChatRepository @Inject constructor(
    private val chatService: ChatService
) : ChatRepository {
    override suspend fun getChatResponse(
        ingredients: String,
        diseases: String,
        dislikeIngredients: String,
        prompt: String,
    ) = flow {
        coroutineScope {
            val responseResult = chatService.getChatResponse(
                ingredients,
                diseases,
                dislikeIngredients,
                prompt
            )

            if (responseResult.isSuccessful) {
                val inputReader = responseResult.body()?.byteStream()?.bufferedReader() ?: throw Exception()

                try {
                    while (isActive) {
                        val line = inputReader.readLine() ?: null

                        if (line == null) {
                            try {
                                emit(null)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                            break
                        } else if (line.startsWith("data:")) {
                            try {
                                emit(line.substring(5))
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                } catch (e: IOException) {
                    throw Exception(e)
                } finally {
                    inputReader.close()
                }
            } else {
                throw HttpException(responseResult)
            }
        }
    }
}