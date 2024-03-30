package com.s005.fif.user.ui.onboarding

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.s005.fif.R
import com.s005.fif.common.data.LikeFoodItemData
import com.s005.fif.common.data.LikeFoodListData
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.UserViewModel

@Composable
fun LikeFoodPage(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    goPrevPage: () -> Unit,
    goNextPage: () -> Unit,
) {
    BackHandler {
        goPrevPage()
    }

    Log.d("로그", " - LikeFoodBody() 호출됨 / ${userViewModel.onboardingState}")

    LikeFoodBody(
        modifier = modifier
            .fillMaxSize(),
        goPrevPage = goPrevPage,
        goNextPage = goNextPage,
        likeFoodList = LikeFoodListData.list,
        onItemClicked = { isChecked, item ->
            userViewModel.checkLikeFood(isChecked, item)
        },
        checkedItemList = userViewModel.onboardingState.preferMenu
    )
}

@Composable
fun LikeFoodBody(
    modifier: Modifier = Modifier,
    goPrevPage: () -> Unit,
    goNextPage: () -> Unit,
    likeFoodList: List<LikeFoodItemData>,
    onItemClicked: (Boolean, LikeFoodItemData) -> Unit,
    checkedItemList: List<LikeFoodItemData>
) {
    Column(
        modifier = modifier
            .padding(horizontal = 30.dp)
            .padding(bottom = 20.dp)
    ) {
        UserOnboardingControlBar(
            modifier = Modifier,
            goPrevPage = goPrevPage,
            goNextPage = goNextPage
        )

        UserOnboardingPageTitle(
            titleText = stringResource(id = R.string.text_like_food_title)
        )

        LazyVerticalGrid(
            modifier = Modifier
                .padding(top = 60.dp),
            columns = GridCells.Fixed(2)
        ) {
            itemsIndexed(
                items = likeFoodList,
                key = { _, item ->
                    item.foodId
                }
            ) { _, item ->
                LikeFoodCheckBox(
                    item = item,
                    onItemClicked = onItemClicked,
                    isChecked = checkedItemList.contains(item)
                )
            }
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        UserOnboardingBtn(
            modifier = Modifier,
            onClick = { goNextPage() },
            text = stringResource(id = R.string.text_btn_next)
        )
    }
}

@Composable
fun LikeFoodCheckBox(
    modifier: Modifier = Modifier,
    item: LikeFoodItemData,
    onItemClicked: (Boolean, LikeFoodItemData) -> Unit,
    isChecked: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(10.dp))
            .toggleable(
                value = isChecked,
                onValueChange = {
                    onItemClicked(it, item)
                },
                role = Role.Checkbox
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null, // null recommended for accessibility with screenreaders
            colors = CheckboxDefaults.colors(
                uncheckedColor = colorScheme.primary
            )
        )

        Text(
            text = item.name,
            style = Typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}