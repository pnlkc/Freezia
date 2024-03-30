package com.s005.fif.chat.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s005.fif.chat.data.ChatListItem
import com.s005.fif.chat.data.ChatResultObject
import com.s005.fif.chat.data.ChatType
import com.s005.fif.chat.data.DefaultChatRepository
import com.s005.fif.chat.dto.ChatResultRecipeListItem
import com.s005.fif.chat.dto.ChatResultResponse
import com.s005.fif.chat.dto.toChatResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: DefaultChatRepository,
) : ViewModel() {
    var prompt by mutableStateOf("")
    val chatList = mutableStateListOf<ChatListItem>()
    var recipeList = mutableStateListOf<ChatResultRecipeListItem>()
    var canChat by mutableStateOf(true)
    var isReplyDone by mutableStateOf(false)

    suspend fun getChatResponse(
        ingredients: String,
        diseases: String,
        dislikeIngredients: String,
    ) {
        canChat = false
        isReplyDone = false
        ChatResultObject.chatResult = ""

        val promptInput = prompt

        prompt = ""
        chatList.add(
            ChatListItem(
                ChatType.MyChat,
                promptInput
            )
        )

        chatList.add(
            ChatListItem(
                ChatType.GPTChat,
                "",
            )
        )

        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.getChatResponse(ingredients, diseases, dislikeIngredients, promptInput)
                .catch {
                    Log.d("로그", "ChatViewModel - getChatResponse() 호출됨 / catch: ${it}")
                }
                .collect { chatStream ->
                    if (chatStream != null) {
                        if (ChatResultObject.chatResult.contains("\"reply\": \"") && !isReplyDone) {
                            if (chatStream.contains("\"")) {
                                isReplyDone = true
                            } else {
                                chatList[chatList.lastIndex] = chatList[chatList.lastIndex].copy(
                                    content = chatList[chatList.lastIndex].content + chatStream
                                )
                            }
                        }

                        ChatResultObject.chatResult += chatStream

                        Log.d(
                            "로그",
                            "ChatViewModel - getChatResponse() 호출됨 / chatResult: ${chatStream}"
                        )
                    } else { // 채팅 응답이 완료된 상황
                        Log.d("로그", "ChatViewModel - getChatResponse() 호출됨 / 채팅 응답 완료")
                        val recipeDataResponse = Json.decodeFromString<ChatResultResponse>(
                            ChatResultObject.chatResult
                        )

                        val chatResult = recipeDataResponse.toChatResult()

                        recipeList.addAll(chatResult.recipeList)

                        chatList[chatList.lastIndex] = chatList[chatList.lastIndex].copy(
                            chatType = ChatType.GPTChat,
                            content = chatResult.reply,
                            recipeList = chatResult.recipeList,
                            recommendPrompt = chatResult.recommendList
                        )

                        canChat = true

                        Log.d(
                            "로그",
                            "ChatViewModel - getChatResponse() 호출됨 / chatList : ${chatList}"
                        )

                        Log.d(
                            "로그",
                            "ChatViewModel - getChatResponse() 호출됨 / ChatResultObject.chatResult : ${ChatResultObject.chatResult}"
                        )
                    }
                }
        }
    }

    fun clearData() {
        prompt = ""
        chatList.clear()
        recipeList.clear()
        canChat = true
        isReplyDone = false
        ChatResultObject.chatResult = ""


//        addItem()
    }

    // TODO. 테스트용 메소드 추후 삭제 필요
    fun addItem() {
        chatList.add(
            ChatListItem(ChatType.MyChat, "레시피 추천해줘")
        )

        chatList.add(
            ChatListItem(
                ChatType.GPTChat,
                "냉장고의 재료를 활용해 '김치 목살 스테이크'를 추천합니다. 고기의 풍미와 김치의 매콤함이 조화로운 요리입니다.",
                Json.decodeFromString<ChatResultResponse>(
                    "{\"reply\":\"냉장고의 재료를 활용해 '김치 목살 스테이크'를 추천합니다. 고기의 풍미와 김치의 매콤함이 조화로운 요리입니다.\",\"recommendList\":[\"쉽게 만들 수 있는 저녁 메뉴 레시피를 알려줘\",\"다이어트에 좋은 저칼로리 요리를 추천해줘\",\"식사 후 디저트로 즐길 수 있는 간단한 레시피를 알려줘\"],\"recipeList\":[{\"name\":\"김치 목살 스테이크\",\"ingredientList\":[{\"name\":\"목살\",\"amounts\":\"200\",\"unit\":\"g\"},{\"name\":\"김치\",\"amounts\":\"100\",\"unit\":\"g\"},{\"name\":\"대파\",\"amounts\":\"1\",\"unit\":\"대\"},{\"name\":\"마늘\",\"amounts\":\"2\",\"unit\":\"쪽\"},{\"name\":\"사과\",\"amounts\":\"1/4\",\"unit\":\"개\"},{\"name\":\"레몬\",\"amounts\":\"1/2\",\"unit\":\"개\"}],\"seasoningList\":[{\"name\":\"간장\",\"amounts\":\"2\",\"unit\":\"스푼\"},{\"name\":\"참기름\",\"amounts\":\"1\",\"unit\":\"스푼\"},{\"name\":\"설탕\",\"amounts\":\"1\",\"unit\":\"스푼\"},{\"name\":\"식초\",\"amounts\":\"1\",\"unit\":\"스푼\"}],\"cookTime\":\"30분\",\"carlorie\":\"약 750 kcal\",\"servings\":\"2인분\",\"recipeType\":\"볶음요리\",\"recipeSteps\":[{\"type\":\"재료 손질\",\"name\":\"재료 준비\",\"description\":\"목살을 한 입 크기로 썰고, 김치를 물기 없이 거칠게 썰어 준비합니다. 대파와 마늘은 잘게 다지고, 사과와 레몬은 얇게 슬라이스합니다.\",\"duration\":600,\"tip\":\"목살의 경우, 질긴 부분은 제거해주세요.\"},{\"type\":\"조리\",\"name\":\"목살 구이\",\"description\":\"팬에 기름을 두르고 목살을 중불에서 양면이 골고루 익을 때까지 구웁니다.\",\"duration\":600,\"tip\":\"고기는 한 번에 다 구우려 하지 말고, 여유 있게 구워주세요.\"},{\"type\":\"조리\",\"name\":\"김치 볶음\",\"description\":\"다른 팬에 마늘과 대파를 볶다가 김치를 추가하여 중불에서 볶습니다. 김치가 살짝 익으면 간장, 참기름, 설탕, 식초를 넣어 간을 맞춥니다.\",\"duration\":600,\"tip\":\"김치가 타지 않도록 잘 저어가며 볶아주세요.\"},{\"type\":\"조리\",\"name\":\"구이와 볶음 합체\",\"description\":\"구운 목살 위에 볶은 김치를 올리고, 그 위에 사과와 레몬 슬라이스를 장식합니다.\",\"duration\":300,\"tip\":\"사과와 레몬은 상큼한 맛을 더해줍니다.\"},{\"type\":\"마무리\",\"name\":\"서빙\",\"description\":\"완성된 김치 목살 스테이크를 그릇에 담아 서빙합니다.\",\"duration\":60,\"tip\":\"잘 구운 목살과 볶은 김치의 조합을 즐겨보세요.\"}]}]}"
                ).toChatResult().recipeList,
                Json.decodeFromString<ChatResultResponse>(
                    "{\"reply\":\"냉장고의 재료를 활용해 '김치 목살 스테이크'를 추천합니다. 고기의 풍미와 김치의 매콤함이 조화로운 요리입니다.\",\"recommendList\":[\"쉽게 만들 수 있는 저녁 메뉴 레시피를 알려줘\",\"다이어트에 좋은 저칼로리 요리를 추천해줘\",\"식사 후 디저트로 즐길 수 있는 간단한 레시피를 알려줘\"],\"recipeList\":[{\"name\":\"김치 목살 스테이크\",\"ingredientList\":[{\"name\":\"목살\",\"amounts\":\"200\",\"unit\":\"g\"},{\"name\":\"김치\",\"amounts\":\"100\",\"unit\":\"g\"},{\"name\":\"대파\",\"amounts\":\"1\",\"unit\":\"대\"},{\"name\":\"마늘\",\"amounts\":\"2\",\"unit\":\"쪽\"},{\"name\":\"사과\",\"amounts\":\"1/4\",\"unit\":\"개\"},{\"name\":\"레몬\",\"amounts\":\"1/2\",\"unit\":\"개\"}],\"seasoningList\":[{\"name\":\"간장\",\"amounts\":\"2\",\"unit\":\"스푼\"},{\"name\":\"참기름\",\"amounts\":\"1\",\"unit\":\"스푼\"},{\"name\":\"설탕\",\"amounts\":\"1\",\"unit\":\"스푼\"},{\"name\":\"식초\",\"amounts\":\"1\",\"unit\":\"스푼\"}],\"cookTime\":\"30분\",\"carlorie\":\"약 750 kcal\",\"servings\":\"2인분\",\"recipeType\":\"볶음요리\",\"recipeSteps\":[{\"type\":\"재료 손질\",\"name\":\"재료 준비\",\"description\":\"목살을 한 입 크기로 썰고, 김치를 물기 없이 거칠게 썰어 준비합니다. 대파와 마늘은 잘게 다지고, 사과와 레몬은 얇게 슬라이스합니다.\",\"duration\":600,\"tip\":\"목살의 경우, 질긴 부분은 제거해주세요.\"},{\"type\":\"조리\",\"name\":\"목살 구이\",\"description\":\"팬에 기름을 두르고 목살을 중불에서 양면이 골고루 익을 때까지 구웁니다.\",\"duration\":600,\"tip\":\"고기는 한 번에 다 구우려 하지 말고, 여유 있게 구워주세요.\"},{\"type\":\"조리\",\"name\":\"김치 볶음\",\"description\":\"다른 팬에 마늘과 대파를 볶다가 김치를 추가하여 중불에서 볶습니다. 김치가 살짝 익으면 간장, 참기름, 설탕, 식초를 넣어 간을 맞춥니다.\",\"duration\":600,\"tip\":\"김치가 타지 않도록 잘 저어가며 볶아주세요.\"},{\"type\":\"조리\",\"name\":\"구이와 볶음 합체\",\"description\":\"구운 목살 위에 볶은 김치를 올리고, 그 위에 사과와 레몬 슬라이스를 장식합니다.\",\"duration\":300,\"tip\":\"사과와 레몬은 상큼한 맛을 더해줍니다.\"},{\"type\":\"마무리\",\"name\":\"서빙\",\"description\":\"완성된 김치 목살 스테이크를 그릇에 담아 서빙합니다.\",\"duration\":60,\"tip\":\"잘 구운 목살과 볶은 김치의 조합을 즐겨보세요.\"}]}]}"
                ).toChatResult().recommendList
            )
        )

        recipeList.add(
            Json.decodeFromString<ChatResultResponse>(
                "{\"reply\":\"냉장고의 재료를 활용해 '김치 목살 스테이크'를 추천합니다. 고기의 풍미와 김치의 매콤함이 조화로운 요리입니다.\",\"recommendList\":[\"쉽게 만들 수 있는 저녁 메뉴 레시피를 알려줘\",\"다이어트에 좋은 저칼로리 요리를 추천해줘\",\"식사 후 디저트로 즐길 수 있는 간단한 레시피를 알려줘\"],\"recipeList\":[{\"name\":\"김치 목살 스테이크\",\"ingredientList\":[{\"name\":\"목살\",\"amounts\":\"200\",\"unit\":\"g\"},{\"name\":\"김치\",\"amounts\":\"100\",\"unit\":\"g\"},{\"name\":\"대파\",\"amounts\":\"1\",\"unit\":\"대\"},{\"name\":\"마늘\",\"amounts\":\"2\",\"unit\":\"쪽\"},{\"name\":\"사과\",\"amounts\":\"1/4\",\"unit\":\"개\"},{\"name\":\"레몬\",\"amounts\":\"1/2\",\"unit\":\"개\"}],\"seasoningList\":[{\"name\":\"간장\",\"amounts\":\"2\",\"unit\":\"스푼\"},{\"name\":\"참기름\",\"amounts\":\"1\",\"unit\":\"스푼\"},{\"name\":\"설탕\",\"amounts\":\"1\",\"unit\":\"스푼\"},{\"name\":\"식초\",\"amounts\":\"1\",\"unit\":\"스푼\"}],\"cookTime\":\"30분\",\"carlorie\":\"약 750 kcal\",\"servings\":\"2인분\",\"recipeType\":\"볶음요리\",\"recipeSteps\":[{\"type\":\"재료 손질\",\"name\":\"재료 준비\",\"description\":\"목살을 한 입 크기로 썰고, 김치를 물기 없이 거칠게 썰어 준비합니다. 대파와 마늘은 잘게 다지고, 사과와 레몬은 얇게 슬라이스합니다.\",\"duration\":600,\"tip\":\"목살의 경우, 질긴 부분은 제거해주세요.\"},{\"type\":\"조리\",\"name\":\"목살 구이\",\"description\":\"팬에 기름을 두르고 목살을 중불에서 양면이 골고루 익을 때까지 구웁니다.\",\"duration\":600,\"tip\":\"고기는 한 번에 다 구우려 하지 말고, 여유 있게 구워주세요.\"},{\"type\":\"조리\",\"name\":\"김치 볶음\",\"description\":\"다른 팬에 마늘과 대파를 볶다가 김치를 추가하여 중불에서 볶습니다. 김치가 살짝 익으면 간장, 참기름, 설탕, 식초를 넣어 간을 맞춥니다.\",\"duration\":600,\"tip\":\"김치가 타지 않도록 잘 저어가며 볶아주세요.\"},{\"type\":\"조리\",\"name\":\"구이와 볶음 합체\",\"description\":\"구운 목살 위에 볶은 김치를 올리고, 그 위에 사과와 레몬 슬라이스를 장식합니다.\",\"duration\":300,\"tip\":\"사과와 레몬은 상큼한 맛을 더해줍니다.\"},{\"type\":\"마무리\",\"name\":\"서빙\",\"description\":\"완성된 김치 목살 스테이크를 그릇에 담아 서빙합니다.\",\"duration\":60,\"tip\":\"잘 구운 목살과 볶은 김치의 조합을 즐겨보세요.\"}]}]}"
            ).toChatResult().recipeList.first()
        )
    }

    fun getRecipeIdx(item: ChatResultRecipeListItem): Int {
        return recipeList.indexOf(item)
    }
}