package com.s005.fif.user.ui.onboarding

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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

@Composable
fun IllnessPage(
    modifier: Modifier = Modifier,
    goPrevPage: () -> Unit,
    goNextPage: () -> Unit
) {
    BackHandler {
        goPrevPage()
    }

    IllnessBody(
        modifier = modifier
            .fillMaxSize(),
        goPrevPage = goPrevPage,
        goNextPage = goNextPage
    )
}

@Composable
fun IllnessBody(
    modifier: Modifier = Modifier,
    goPrevPage: () -> Unit,
    goNextPage: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 30.dp)
            .padding(bottom = 30.dp)
    ) {
        UserOnboardingControlBar(
            modifier = Modifier,
            goPrevPage = goPrevPage,
            goNextPage = goNextPage
        )

        UserOnboardingPageTitle(
            titleText = stringResource(id = R.string.text_illness_title)
        )

        UserProfileTextField(
            content = "",
            setContent = { },
            hintText = stringResource(id = R.string.text_field_hint_illness)
        )

        IllnessSearchResultLazyColumn()

        HorizontalDivider(
            modifier = Modifier,
            thickness = 1.dp,
            color = Color.Black.copy(0.1f),
        )

        IllnessSelectLazyRow()

        Spacer(
            modifier = Modifier.weight(1f)
        )

        UserOnboardingBtn(
            modifier = Modifier,
            onClick = { goNextPage() },
            text = stringResource(id = R.string.text_btn_start)
        )
    }
}

@Composable
fun IllnessSearchResultLazyColumn(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        itemsIndexed(
            items = listOf<String>(
                "고혈압",
                "저혈압",
            ),
            key = { _, item ->
                item
            }
        ) { _, item ->
            IllnessSearchResultItem(
                item = item
            )
        }
    }
}

@Composable
fun IllnessSearchResultItem(
    modifier: Modifier = Modifier,
    item: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .size(20.dp)
                .border(1.dp, Color.Black, CircleShape)
        ) {
            Image(
                modifier = modifier
                    .size(20.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.add),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Black),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = item,
            style = Typography.bodyMedium,
            color = Color.Black.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun IllnessSelectLazyRow(
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        itemsIndexed(
            items = listOf<String>(
                "고혈압"
            ),
            key = { _, item ->
                item
            }
        ) { _, item ->
            IllnessSelectItem(
                modifier = Modifier,
                item = item,
                onClick = {  }
            )
        }
    }
}

@Composable
fun IllnessSelectItem(
    modifier: Modifier = Modifier,
    item: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(3.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 5.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = item,
                style = Typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.5f)
            )

            Image(
                modifier = modifier
                    .size(20.dp),
                painter = painterResource(id = R.drawable.close),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileTextField(
    modifier: Modifier = Modifier,
    content: String,
    setContent: (String) -> Unit,
    hintText: String
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
            .padding(top = 50.dp)
            .background(Color.Transparent)
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(30.dp)),
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
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = stringResource(id = R.string.description_icon_search),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    contentScale = ContentScale.Fit
                )
            },
            placeholder = {
                Text(
                    text = hintText,
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