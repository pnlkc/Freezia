package com.s005.fif.recipe.ui.chat

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.dto.RecipeHistoryItem
import com.s005.fif.user.ui.recipe_history.ui.RecipeHistoryLazyVerticalGridItem
import com.s005.fif.utils.ScreenSizeUtil
import com.s005.fif.utils.ScreenSizeUtil.toDpSize
import com.s005.fif.utils.StringLineChangeUtil.toNonBreakingString

@Composable
fun RecipeChatScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        RecipeChatTopBar(
            modifier = modifier
                .padding(horizontal = 10.dp),
            navigateUp = { navigateUp() }
        )

        RecipeChatBody(
            modifier = modifier
                .weight(1f)
        )

        Surface(
            modifier = Modifier,
            shadowElevation = 10.dp,
            color = colorScheme.background
        ) {
            RecipeChatTextField(
                content = "",
                setContent = { },
                onClick = { }
            )
        }
    }
}

@Composable
fun RecipeChatTopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .size(25.dp)
                .clickable { navigateUp() },
            painter = painterResource(id = R.drawable.back),
            contentDescription = stringResource(id = R.string.description_btn_go_back)
        )
    }
}

@Composable
fun RecipeChatBody(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            MyChat(text = "냉장고에 있는 재료로 만들 수 있는 덮밥 레시피 알려줘")
        }

        item {
            GPTChat(text = "냉장고에 있는 스팸, 마요네즈, 김치를 사용해 레시피를 생성했습니다")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeChatTextField(
    modifier: Modifier = Modifier,
    content: String,
    setContent: (String) -> Unit,
    onClick: () -> Unit,
) {
    // TODO. 진짜 텍스트로 변경 필요
    val text = remember {
        mutableStateOf("")
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    BasicTextField(
        value = text.value,
        onValueChange = { text.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                RoundedCornerShape(30.dp)
            ),
        interactionSource = interactionSource,
        enabled = true,
        textStyle = Typography.bodyMedium,
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
    ) {
        TextFieldDefaults.DecorationBox(
            value = text.value,
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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    contentScale = ContentScale.Fit
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.text_chat_send),
                    style = Typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            contentPadding = PaddingValues(start = 15.dp)
        )
    }
}

@Composable
fun MyChat(
    modifier: Modifier = Modifier,
    text: String
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
    text: String
) {
    val cornerDp = 15.dp

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .widthIn(max = ScreenSizeUtil.widthDp.toDpSize(70))
                    .align(Alignment.CenterStart)
                    .clip(
                        RoundedCornerShape(
                            topEnd = cornerDp,
                            bottomStart = cornerDp,
                            bottomEnd = cornerDp
                        )
                    )
                    .background(Color(0xFFFFB638).copy(alpha = 0.8f))
                    .padding(horizontal = 15.dp, vertical = 10.dp),
                text = text.toNonBreakingString(),
                style = Typography.bodyMedium,
            )
        }

        GPTRecipeLazyRow(
            list = listOf()
        )

        GPTRecommendPromptLazyColumn()
    }
}

@Composable
fun GPTRecipeLazyRow(
    modifier: Modifier = Modifier,
    list: List<RecipeHistoryItem>
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(
            items = list,
            key = { _, item ->
                item.imgUrl
            }
        ) { _, item ->
            RecipeHistoryLazyVerticalGridItem(
                modifier = modifier
                    .widthIn(max = ScreenSizeUtil.widthDp.toDpSize(35)),
                item = item,
                onClick = {  }
            )
        }
    }
}

@Composable
fun GPTRecommendPromptLazyColumn(
    modifier: Modifier = Modifier,
    list: List<String> = listOf(
        "가장 최근에 만든 요리와 비슷한 재료를 사용하는 레시피를 알려줘",
        "간식으로 먹고 싶은데 칼로리를 절반으로 줄인 레시피를 알려줘",
        "덮밥말고 볶음밥 레시피로 알려줘"
    )
) {
    Column(
        modifier = modifier
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        list.forEach { item ->
            GPTRecommendPromptItem(
                item = item,
                onClick = {  }
            )
        }
    }
}

@Composable
fun GPTRecommendPromptItem(
    modifier: Modifier = Modifier,
    item: String,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(Color.White)
            .border(1.dp, colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(50.dp))
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 15.dp),
        text = item.toNonBreakingString(),
        style = Typography.bodySmall
    )
}