package com.s005.fif.shopping_list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.s005.fif.common.data.IngredientListData
import com.s005.fif.recipe.dto.IngredientItem
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.UserViewModel
import com.s005.fif.user.ui.onboarding.UserOnboardingBtn
import com.s005.fif.user.ui.onboarding.UserProfileTextField
import com.s005.fif.user.ui.profile.UserProfileTopBar
import kotlinx.coroutines.launch

@Composable
fun ShoppingListAddScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    shoppingListViewModel: ShoppingListViewModel,
    navigateUp: () -> Unit,
    navigateToRecipeHistory: () -> Unit,
    navigateToUserSelect: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .padding(bottom = 20.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        UserProfileTopBar(
            navigateUp = navigateUp,
            memberInfo = userViewModel.memberInfo,
            navigateToRecipeHistory = navigateToRecipeHistory,
            navigateToUserSelect = navigateToUserSelect
        )

        ShoppingListAddBody(
            isCheckedChanged = { id, isChecked ->
                shoppingListViewModel.checkIngredientCheckList(id, isChecked)
            },
            ingredientCheckList = shoppingListViewModel.ingredientCheckList,
            inputText = shoppingListViewModel.inputText,
            inputTextChange = {
                shoppingListViewModel.inputText = it
            },
            onEditBtnClicked = {
                coroutineScope.launch {
                    shoppingListViewModel.addIngredientShoppingList()
                    navigateUp()
                }
            }
        )
    }
}

@Composable
fun ShoppingListAddBody(
    modifier: Modifier = Modifier,
    isCheckedChanged: (Int, Boolean) -> Unit,
    ingredientCheckList: List<IngredientItem>,
    inputText: String,
    inputTextChange: (String) -> Unit,
    onEditBtnClicked: () -> Unit
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
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(id = R.string.text_edit_shopping_list),
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                UserProfileTextField(
                    modifier = Modifier,
                    content = inputText,
                    setContent = inputTextChange,
                    hintText = stringResource(id = R.string.text_field_hint_dislike_ingredient),
                    hasTopPadding = false
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                ) {
                    itemsIndexed(
                        items = ingredientCheckList.filter { it.name.contains(inputText) },
                        key = { _, item ->
                            item.ingredientId
                        }
                    ) { _, item ->
                        ShoppingListAddLazyColumnItem(
                            item = item,
                            isDone = false,
                            isChecked = item.isChecked,
                            isCheckedChanged = {
                                isCheckedChanged(item.ingredientId, !item.isChecked)
                            }
                        )
                    }
                }

                UserOnboardingBtn(
                    modifier = Modifier,
                    onClick = { onEditBtnClicked() },
                    text = stringResource(id = R.string.text_btn_edit)
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ShoppingListAddLazyColumnItem(
    modifier: Modifier = Modifier,
    item: IngredientItem,
    isDone: Boolean,
    isChecked: Boolean,
    isCheckedChanged: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .toggleable(
                value = isChecked,
                onValueChange = { isCheckedChanged() },
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
                model = IngredientListData.nameMap[item.name],
                contentDescription = stringResource(id = R.string.description_ingredient_img),
                contentScale = ContentScale.Crop
            )

            Text(
                text = item.name,
                style = Typography.bodyMedium,
                textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None
            )
        }

        Checkbox(
            checked = isChecked,
            onCheckedChange = null, // null recommended for accessibility with screenreaders
            colors = CheckboxDefaults.colors(
                uncheckedColor = MaterialTheme.colorScheme.onSecondary
            )
        )
    }
}