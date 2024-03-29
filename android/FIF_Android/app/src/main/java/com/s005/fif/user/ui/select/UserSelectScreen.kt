package com.s005.fif.user.ui.select

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.dto.UserItem
import com.s005.fif.user.ui.UserViewModel
import com.s005.fif.utils.ScreenSizeUtil
import com.s005.fif.utils.ScreenSizeUtil.toDpSize
import kotlinx.coroutines.launch

@Composable
fun UserSelectScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    navigateToUserOnboarding: () -> Unit,
    navigateToMain: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    UserSelectBody(
        modifier = modifier
            .fillMaxSize(),
        userClicked = { memberId ->
            coroutineScope.launch {
                userViewModel.getAccessToken(memberId)
                userViewModel.getMemberInfo()

                if (userViewModel.memberInfo!!.onboardYn) {
                    navigateToMain()
                } else {
                    userViewModel.clearOnboarding()
                    navigateToUserOnboarding()
                }
            }
        },
        userList = { userViewModel.userList }
    )
}

@Composable
fun UserSelectBody(
    modifier: Modifier = Modifier,
    userClicked: (Int) -> Unit,
    userList: () -> List<UserItem>
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 20.dp),
            text = stringResource(id = R.string.text_select_user),
            textAlign = TextAlign.Center,
            style = Typography.titleLarge,
            color = colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        LazyVerticalGrid(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            columns = GridCells.Adaptive(minSize = ScreenSizeUtil.widthDp.toDpSize(25)),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                UserAddItem(
                    modifier = Modifier,
                )
            }

            itemsIndexed(
                items = userList(),
                key = { _, item ->
                    item.memberId
                }
            ) { _, item ->
                UserSelectItem(
                    modifier = Modifier,
                    item = item,
                    userClicked = userClicked
                )
            }
        }
    }
}

@Composable
fun UserAddItem(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                modifier = modifier
                    .size((ScreenSizeUtil.widthDp / 3 - 40).dp),
                painter = painterResource(id = R.drawable.add_circle),
                contentDescription = stringResource(id = R.string.description_btn_profile_add),
                colorFilter = ColorFilter.tint(colorScheme.primary),
                contentScale = ContentScale.Fit
            )

            Text(
                text = stringResource(id = R.string.text_add_user),
                style = Typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserSelectItem(
    modifier: Modifier = Modifier,
    item: UserItem,
    userClicked: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .clickable { userClicked(item.memberId) }
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GlideImage(
                modifier = modifier
                    .clip(CircleShape)
                    .size((ScreenSizeUtil.widthDp / 3 - 40).dp),
                model = item.imgUrl,
                contentDescription = stringResource(id = R.string.description_img_profile),
                contentScale = ContentScale.Crop,
                loading = placeholder(R.drawable.account),
                failure = placeholder(R.drawable.account)
            )

            Text(
                text = item.name,
                style = Typography.bodyMedium
            )
        }
    }
}