package com.s005.fif.user.ui.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.s005.fif.R
import com.s005.fif.common.data.DiseaseItemData
import com.s005.fif.common.data.DiseaseListData
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.UserViewModel

@Composable
fun IllnessPage(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    goPrevPage: () -> Unit,
    goNextPage: () -> Unit,
) {
    BackHandler {
        goPrevPage()
    }

    IllnessBody(
        modifier = modifier
            .fillMaxSize(),
        goPrevPage = goPrevPage,
        goNextPage = goNextPage,
        inputText = userViewModel.diseaseInputText,
        inputTextChange = {
            userViewModel.diseaseInputText = it
        },
        onItemClicked = { isAdd, item ->
            userViewModel.clickDiseaseItem(isAdd, item)
        },
        checkedDiseaseList = userViewModel.onboardingState.diseases
    )
}

@Composable
fun IllnessBody(
    modifier: Modifier = Modifier,
    goPrevPage: () -> Unit,
    goNextPage: () -> Unit,
    inputText: String,
    inputTextChange: (String) -> Unit,
    onItemClicked: (Boolean, DiseaseItemData) -> Unit,
    checkedDiseaseList: List<DiseaseItemData>
) {
    Column(
        modifier = modifier
            .padding(horizontal = 30.dp)
            .padding(bottom = 20.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                UserOnboardingControlBar(
                    modifier = Modifier,
                    goPrevPage = goPrevPage,
                    goNextPage = goNextPage
                )
            }

            item {
                UserOnboardingPageTitle(
                    titleText = stringResource(id = R.string.text_illness_title)
                )
            }

            item {
                UserProfileTextField(
                    content = inputText,
                    setContent = { inputTextChange(it) },
                    hintText = stringResource(id = R.string.text_field_hint_illness)
                )
            }

            itemsIndexed(
                items = if (inputText.isBlank()) {
                    DiseaseListData.list
                } else {
                    DiseaseListData.list.filter { it.name.contains(inputText) }
                },
                key = { _, item ->
                    item.diseaseId
                }
            ) { _, item ->
                IllnessSearchResultItem(
                    item = item,
                    onItemClicked = onItemClicked
                )
            }

            item {
                Spacer(modifier = Modifier.size(20.dp))
            }
        }

        HorizontalDivider(
            modifier = Modifier,
            thickness = 1.dp,
            color = Color.Black.copy(0.1f),
        )

        IllnessSelectLazyRow(
            checkedDiseaseList = checkedDiseaseList,
            onItemClicked = onItemClicked
        )

        UserOnboardingBtn(
            modifier = Modifier,
            onClick = { goNextPage() },
            text = stringResource(id = R.string.text_btn_start)
        )
    }
}

@Composable
fun IllnessSearchResultItem(
    modifier: Modifier = Modifier,
    item: DiseaseItemData,
    onItemClicked: (Boolean, DiseaseItemData) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClicked(true, item) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.name,
            style = Typography.bodyMedium,
            color = Color.Black.copy(alpha = 0.5f)
        )

        Image(
            modifier = modifier
                .clip(CircleShape)
                .border(1.dp, Color.Black, CircleShape)
                .size(20.dp),
            painter = painterResource(id = R.drawable.add),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.Black),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun IllnessSelectLazyRow(
    modifier: Modifier = Modifier,
    checkedDiseaseList: List<DiseaseItemData>,
    onItemClicked: (Boolean, DiseaseItemData) -> Unit
) {
    LazyRow(
        modifier = modifier
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        reverseLayout = true
    ) {
        itemsIndexed(
            items = checkedDiseaseList,
            key = { _, item ->
                item.diseaseId
            }
        ) { _, item ->
            IllnessSelectItem(
                modifier = Modifier,
                item = item,
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
fun IllnessSelectItem(
    modifier: Modifier = Modifier,
    item: DiseaseItemData,
    onItemClicked: (Boolean, DiseaseItemData) -> Unit,
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .padding(2.dp),
        shape = RoundedCornerShape(50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        Row(
            modifier = modifier
                .clickable { onItemClicked(false, item) }
                .padding(vertical = 5.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = item.name,
                style = Typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.5f)
            )

            Image(
                modifier = modifier
                    .size(20.dp),
                painter = painterResource(id = R.drawable.close),
                contentDescription = stringResource(id = R.string.description_icon_remove),
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
    hintText: String,
    hasTopPadding: Boolean = true
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    BasicTextField(
        value = content,
        onValueChange = { setContent(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = if (hasTopPadding) 30.dp else 0.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(Color.Transparent)
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(50.dp)),
        interactionSource = interactionSource,
        enabled = true,
        textStyle = Typography.bodyMedium,
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
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