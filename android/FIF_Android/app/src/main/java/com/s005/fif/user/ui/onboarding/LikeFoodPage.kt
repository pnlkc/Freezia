package com.s005.fif.user.ui.onboarding

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography

@Composable
fun LikeFoodPage(
    modifier: Modifier = Modifier,
    goPrevPage: () -> Unit,
    goNextPage: () -> Unit
) {
    BackHandler {
        goPrevPage()
    }

    LikeFoodBody(
        modifier = modifier
            .fillMaxSize(),
        goPrevPage = goPrevPage,
        goNextPage = goNextPage
    )
}

@Composable
fun LikeFoodBody(
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
            titleText = stringResource(id = R.string.text_like_food_title)
        )

        LazyVerticalGrid(
            modifier = Modifier
                .padding(top = 60.dp),
            columns = GridCells.Fixed(2)
        ) {
            itemsIndexed(
                items = listOf<String>(
                    "한식",
                    "중식",
                    "양식",
                    "일식",
                    "면 요리",
                    "국물 요리",
                    "볶음 요리",
                    "찜 요리",
                    "튀김 요리"
                ),
                key = { _, item ->
                    item
                }
            ) { _, item ->
                LikeFoodCheckBox(
                    item = item
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
    item: String,
) {
    val (checkedState, onStateChange) = remember { mutableStateOf(false) }

    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .toggleable(
                value = checkedState,
                onValueChange = { onStateChange(!checkedState) },
                role = Role.Checkbox
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = null // null recommended for accessibility with screenreaders
        )

        Text(
            text = item,
            style = Typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}