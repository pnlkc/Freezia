package com.s005.fif.user.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.UserViewModel
import com.s005.fif.user.ui.onboarding.UserOnboardingBtn

@Composable
fun RecipePreferenceSettingScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel(),
    navigateToUserProfile: () -> Unit,
    navigateUp: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        UserProfileTopBar(
            navigateUp = navigateUp,
            memberInfo = userViewModel.memberInfo
        )

        RecipePreferenceSettingBody()
    }
}

@Composable
fun RecipePreferenceSettingBody(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        RecipePreferenceSettingColumn(
            modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        )

        UserOnboardingBtn(
            modifier = Modifier,
            onClick = {  },
            text = stringResource(id = R.string.text_btn_save)
        )
    }
}

@Composable
fun RecipePreferenceSettingColumn(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        UserProfileColumnText(
            text = stringResource(id = R.string.text_recipe_preference_setting_title)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                RecipePreferenceSettingRow(
                    hintText = stringResource(id = R.string.text_like_food_setting_hint),
                    list = listOf("한식", "일식", "국물 요리")
                )

                HorizontalDivider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.1f)
                )

                RecipePreferenceSettingRow(
                    hintText = stringResource(id = R.string.text_dislike_ingredient_setting_hint),
                    list = listOf("오이", "고수")
                )

                HorizontalDivider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.1f)
                )

                RecipePreferenceSettingRow(
                    hintText = stringResource(id = R.string.text_illness_setting_hint),
                    list = listOf("당뇨")
                )
            }
        }
    }
}

@Composable
fun RecipePreferenceSettingRow(
    modifier: Modifier = Modifier,
    hintText: String,
    list: List<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        RecipePreferenceSettingTextField(
            content = "",
            setContent = {  },
            hintText = hintText,
            onClick = {  }
        )

        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            itemsIndexed(
                items = list,
                key = { _, item ->
                    item
                }
            ) { _, item ->
                RecipePreferenceSettingLazyRowItem(
                    item = item,
                    onClick = {  }
                )
            }
        }
    }
}

@Composable
fun RecipePreferenceSettingLazyRowItem(
    modifier: Modifier = Modifier,
    item: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(colorScheme.primary)
            .clickable { onClick() }
            .padding(vertical = 5.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            modifier = modifier
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.primary),
            text = item,
            style = Typography.bodyMedium,
            color = colorScheme.onPrimary
        )

        Image(
            modifier = modifier
                .size(15.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.close),
            contentDescription = stringResource(id = R.string.description_icon_remove),
            colorFilter = ColorFilter.tint(Color.White),
            contentScale = ContentScale.Fit
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipePreferenceSettingTextField(
    modifier: Modifier = Modifier,
    content: String,
    setContent: (String) -> Unit,
    hintText: String,
    onClick: () -> Unit
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
            .background(Color.Transparent)
            .border(1.dp, colorScheme.primary, RoundedCornerShape(30.dp)),
        interactionSource = interactionSource,
        enabled = true,
        textStyle = Typography.bodyMedium,
        singleLine = true,
        cursorBrush = SolidColor(colorScheme.primary)
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
                        .background(colorScheme.primary)
                        .clickable { onClick() },
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = stringResource(id = R.string.description_icon_add),
                    colorFilter = ColorFilter.tint(colorScheme.onPrimary),
                    contentScale = ContentScale.Fit
                )
            },
            placeholder = {
                Text(
                    text = hintText,
                    style = Typography.bodyMedium,
                    color = colorScheme.onSecondary
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