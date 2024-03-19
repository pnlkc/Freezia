package com.s005.fif.shopping_list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.profile.RecipePreferenceSettingRow
import com.s005.fif.user.ui.profile.UserProfileColumnText
import com.s005.fif.user.ui.profile.UserProfileTopBar
import com.s005.fif.user.ui.recipe_history.ui.SavedRecipeBody

@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        UserProfileTopBar(
            navigateUp = navigateUp
        )

        ShoppingListBody()
    }
}

@Composable
fun ShoppingListBody(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(25.dp),
                painter = painterResource(id = R.drawable.shopping_cart),
                contentDescription = stringResource(id = R.string.description_btn_go_back),
                tint = colorScheme.primary
            )

            Text(
                text = stringResource(id = R.string.text_go_to_shopping_list),
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

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
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                CheckedItem(
                    modifier = Modifier
                )

                HorizontalDivider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = colorScheme.onSecondary.copy(alpha = 0.1f)
                )

                UncheckedItem(
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun CheckedItem(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "5개의 아이템",
            style = Typography.titleSmall,
            fontWeight = FontWeight.Bold
        )

        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .size(25.dp)
                .clickable { },
            painter = painterResource(id = R.drawable.edit),
            contentDescription = stringResource(id = R.string.description_shopping_list_icon),
            tint = Color.Black
        )
    }

    ShoppingListLazyColumn(
        list = listOf("당근", "대파", "토마토", "양파", "양배추"),
        isDone = false
    )
}

@Composable
fun UncheckedItem(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "체크된 아이템",
            style = Typography.titleSmall,
            fontWeight = FontWeight.Bold
        )

        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable { },
            shape = RoundedCornerShape(20.dp),
            shadowElevation = 2.dp
        ) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(colorScheme.primary)
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                text = "지우기",
                style = Typography.bodyMedium,
                color = Color.White
            )
        }
    }

    ShoppingListLazyColumn(
        list = listOf("파프리카", "피망"),
        isDone = true
    )
}

@Composable
fun ShoppingListLazyColumn(
    modifier: Modifier = Modifier,
    list: List<String>,
    isDone: Boolean
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        itemsIndexed(
            items = list,
            key = { _, item ->
                item
            }
        ) { _, item ->
            ShoppingListLazyColumnItem(
                item = item,
                isDone = isDone
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ShoppingListLazyColumnItem(
    modifier: Modifier = Modifier,
    item: String,
    isDone: Boolean
) {
    val (checkedState, onStateChange) = remember { mutableStateOf(false) }

    Row(
        Modifier
            .fillMaxWidth()
            .toggleable(
                value = checkedState,
                onValueChange = { onStateChange(!checkedState) },
                role = Role.Checkbox
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape),
                model = "https://i.namu.wiki/i/n2LztcrML9hzPww_iKNeMuh34vg48dkmZmGuMEC_e-DSpNoPGwch9nR9FZz9WfVx6nvv5aSDxqlxEG8iA9tcLQ.webp",
                contentDescription = stringResource(id = R.string.description_ingredient_img),
                contentScale = ContentScale.Crop
            )

            Text(
                text = item,
                style = Typography.bodyMedium,
                textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None
            )
        }

        Checkbox(
            checked = checkedState,
            onCheckedChange = null, // null recommended for accessibility with screenreaders
            colors = CheckboxDefaults.colors(
                uncheckedColor = colorScheme.onSecondary
            )
        )
    }
}