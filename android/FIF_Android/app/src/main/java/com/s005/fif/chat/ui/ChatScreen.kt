package com.s005.fif.chat.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.chat.data.ChatListItem
import com.s005.fif.chat.data.ChatResultObject
import com.s005.fif.chat.data.ChatType
import com.s005.fif.chat.dto.ChatResultRecipeListItem
import com.s005.fif.common.data.DiseaseListData
import com.s005.fif.common.data.IngredientListData
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.UserViewModel
import com.s005.fif.utils.ScreenSizeUtil
import com.s005.fif.utils.ScreenSizeUtil.toDpSize
import com.s005.fif.utils.StringLineChangeUtil.toNonBreakingString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    chatViewModel: ChatViewModel,
    navigateUp: () -> Unit,
    navigateToChatRecipeDetail: (Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        ChatTopBar(
            modifier = modifier
                .padding(horizontal = 10.dp),
            navigateUp = { navigateUp() }
        )

        ChatBody(
            modifier = modifier
                .weight(1f),
            chatList = chatViewModel.chatList,
            navigateToChatRecipeDetail = { item ->
                navigateToChatRecipeDetail(chatViewModel.getRecipeIdx(item))
            },
            canChat = chatViewModel.canChat,
            isReplyDone = chatViewModel.isReplyDone,
            onRecommendPromptClicked = { text ->
                if (chatViewModel.canChat) {
                    coroutineScope.launch {
                        chatViewModel.prompt = text
                        chatViewModel.getChatResponse(
                            userViewModel.fridgeIngredientList.distinct().joinToString(", "),
                            userViewModel.memberInfo!!.diseases.map { DiseaseListData.mapIdToItem[it]!! }
                                .joinToString(", "),
                            userViewModel.memberInfo!!.dislikeIngredients.map { IngredientListData.mapIdToItem[it]!! }
                                .joinToString(", "),
                        )
                    }
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.text_wait_prompt),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        Surface(
            modifier = Modifier,
            shadowElevation = 10.dp,
            color = colorScheme.background
        ) {
            ChatTextField(
                content = chatViewModel.prompt,
                setContent = { chatViewModel.prompt = it },
                onClick = {
                    if (chatViewModel.canChat) {
                        coroutineScope.launch {
                            chatViewModel.getChatResponse(
                                userViewModel.fridgeIngredientList.distinct().joinToString(", "),
                                userViewModel.memberInfo!!.diseases.map { DiseaseListData.mapIdToItem[it]!! }
                                    .joinToString(", "),
                                userViewModel.memberInfo!!.dislikeIngredients.map { IngredientListData.mapIdToItem[it]!! }
                                    .joinToString(", "),
                            )
                        }
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.text_wait_prompt),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }
}

@Composable
fun ChatTopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(30.dp),
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .size(25.dp)
                .clickable { navigateUp() }
                .align(Alignment.CenterStart),
            painter = painterResource(id = R.drawable.back),
            contentDescription = stringResource(id = R.string.description_btn_go_back)
        )

        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(id = R.string.text_recipe_gpt),
            style = Typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Composable
fun ChatBody(
    modifier: Modifier = Modifier,
    chatList: List<ChatListItem>,
    navigateToChatRecipeDetail: (ChatResultRecipeListItem) -> Unit,
    canChat: Boolean,
    isReplyDone: Boolean,
    onRecommendPromptClicked: (String) -> Unit,
) {
    val lazyColumnState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        state = lazyColumnState
    ) {
        coroutineScope.launch {
            if (chatList.isNotEmpty()) {
                lazyColumnState.scrollToItem(chatList.lastIndex)
            }
        }

        itemsIndexed(
            items = chatList,
            key = { idx, item ->
                "${idx} ${canChat}"
            }
        ) { _, item ->
            when (item.chatType) {
                ChatType.MyChat -> {
                    MyChat(
                        text = item.content
                    )
                }

                ChatType.GPTChat -> {
                    if (item.content.isNotBlank()) {
                        GPTChat(
                            text = item.content,
                            recipeList = item.recipeList,
                            recommendPromptList = item.recommendPrompt,
                            navigateToChatRecipeDetail = navigateToChatRecipeDetail,
                            canChat = canChat,
                            isReplyDone = isReplyDone,
                            onRecommendPromptClicked = onRecommendPromptClicked
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTextField(
    modifier: Modifier = Modifier,
    content: String,
    setContent: (String) -> Unit,
    onClick: () -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = content,
        onValueChange = setContent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .border(
                1.dp,
                colorScheme.onSecondary.copy(alpha = 0.3f),
                RoundedCornerShape(30.dp)
            ),
        interactionSource = interactionSource,
        enabled = true,
        textStyle = Typography.bodyMedium,
        singleLine = true,
        cursorBrush = SolidColor(colorScheme.primary),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
        keyboardActions = KeyboardActions(onDone = {
            onClick()
            keyboardController?.hide()
        })
    ) {
        TextFieldDefaults.DecorationBox(
            value = content,
            innerTextField = it,
            enabled = true,
            singleLine = true,
            interactionSource = interactionSource,
            visualTransformation = VisualTransformation.None,
            trailingIcon = {
                Image(
                    modifier = modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .clickable { onClick() },
                    painter = painterResource(id = R.drawable.send),
                    contentDescription = stringResource(id = R.string.description_icon_send),
                    colorFilter = ColorFilter.tint(colorScheme.primary),
                    contentScale = ContentScale.Fit
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.text_chat_send),
                    style = Typography.bodyMedium,
                    color = colorScheme.onSecondary
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = colorScheme.primary,
            ),
            contentPadding = PaddingValues(start = 15.dp)
        )
    }
}

@Composable
fun MyChat(
    modifier: Modifier = Modifier,
    text: String,
) {
    val cornerDp = 15.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .widthIn(max = ScreenSizeUtil.widthDp.toDpSize(70))
                .align(Alignment.CenterEnd)
                .clip(
                    RoundedCornerShape(
                        topStart = cornerDp,
                        bottomStart = cornerDp,
                        bottomEnd = cornerDp
                    )
                )
                .background(Color(0xFFFE9738).copy(alpha = 0.8f))
                .padding(horizontal = 15.dp, vertical = 10.dp),
            text = text.toNonBreakingString(),
            style = Typography.bodyMedium
        )
    }
}

@Composable
fun GPTChat(
    modifier: Modifier = Modifier,
    text: String,
    recipeList: List<ChatResultRecipeListItem>,
    recommendPromptList: List<String>,
    navigateToChatRecipeDetail: (ChatResultRecipeListItem) -> Unit,
    canChat: Boolean,
    isReplyDone: Boolean,
    onRecommendPromptClicked: (String) -> Unit,
) {
    val cornerDp = 15.dp

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = modifier
                .widthIn(max = ScreenSizeUtil.widthDp.toDpSize(70))
                .clip(
                    RoundedCornerShape(
                        topEnd = cornerDp,
                        bottomStart = cornerDp,
                        bottomEnd = cornerDp
                    )
                )
                .background(Color(0xFFFFB638).copy(alpha = 0.8f))
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                modifier = Modifier,
                text = text.toNonBreakingString(),
                style = Typography.bodyMedium,
            )

            if (recipeList.isNotEmpty()) {
                GPTRecipeLazyRow(
                    list = recipeList,
                    navigateToChatRecipeDetail = navigateToChatRecipeDetail
                )
            } else if (isReplyDone) {
                GPTLoadingColumn()
            }
        }

        if (recommendPromptList.isNotEmpty()) {
            GPTRecommendPromptLazyColumn(
                list = recommendPromptList,
                canChat = canChat,
                onRecommendPromptClicked = onRecommendPromptClicked
            )
        }
    }
}

@Composable
fun GPTLoadingColumn(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color.Black,
        targetValue = Color.White,
        animationSpec = infiniteRepeatable(tween(2000), RepeatMode.Reverse),
        label = "color"
    )

    Column(
        modifier = modifier
            .height((ScreenSizeUtil.heightDp / 5).dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 30.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(ScreenSizeUtil.heightDp.toDpSize(5)),
            color = colorScheme.primary,
            trackColor = colorScheme.onSecondary.copy(alpha = 0.5f),
            strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap,
        )

        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.text_recipe_progress),
            style = Typography.bodyMedium,
            color = animatedColor
        )
    }
}

@Composable
fun GPTRecipeLazyRow(
    modifier: Modifier = Modifier,
    list: List<ChatResultRecipeListItem>,
    navigateToChatRecipeDetail: (ChatResultRecipeListItem) -> Unit,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(
            items = list,
            key = { idx, item ->
                "${idx} ${item.name}"
            }
        ) { _, item ->
            GPTRecipeLazyVerticalGridItem(
                modifier = modifier
                    .widthIn(max = ScreenSizeUtil.widthDp.toDpSize(70) - 30.dp),
                item = item,
                onItemClicked = { navigateToChatRecipeDetail(item) }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GPTRecipeLazyVerticalGridItem(
    modifier: Modifier = Modifier,
    item: ChatResultRecipeListItem,
    onItemClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height((ScreenSizeUtil.heightDp / 5).dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onItemClicked()
            }
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize(),
            model = ChatResultObject.defaultRecipeImg,
            contentDescription = stringResource(id = R.string.description_recipe_img),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.1f), BlendMode.ColorBurn)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 5.dp,
                alignment = Alignment.Bottom
            )
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(15.dp),
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = stringResource(id = R.string.description_time_icon),
                    tint = Color.White
                )

                Text(
                    text = item.cookTime,
                    style = Typography.bodySmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                modifier = Modifier
                    .padding(start = 2.dp),
                text = item.name,
                style = Typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun GPTRecommendPromptLazyColumn(
    modifier: Modifier = Modifier,
    list: List<String>,
    canChat: Boolean,
    onRecommendPromptClicked: (String) -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        list.forEach { item ->
            GPTRecommendPromptItem(
                item = item,
                onClick = {
                    if (canChat) {
                        onRecommendPromptClicked(item)
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.text_wait_prompt),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }
}

@Composable
fun GPTRecommendPromptItem(
    modifier: Modifier = Modifier,
    item: String,
    onClick: () -> Unit,
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(Color.White)
            .border(1.dp, colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(50.dp))
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 15.dp),
        text = item.toNonBreakingString(),
        style = Typography.bodySmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}