package com.s005.fif.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToUserProfile: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "메인 화면")
        
        Button(
            onClick = { navigateToUserProfile() }
        ) {
            Text(text = "프로필로 가기")
        }
    }
}