package com.s005.fif.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.s005.fif.ui.theme.Typography

object AnnotatedStringUtil {
    @Composable
    fun makeUserProfileString(full: String, spot: String) : AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = Typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(spot)
            }

            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontSize = Typography.bodyMedium.fontSize
                )
            ) {
                append(" ")
                append(full.removePrefix(spot))
            }
        }
    }

    @Composable
    fun makeMainRecommendRecipeString(full: String, spot: String) : AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontSize = Typography.bodyMedium.fontSize
                )
            ) {
                append(full.split(spot)[0])
            }

            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = Typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(spot)
            }

            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontSize = Typography.bodyMedium.fontSize
                )
            ) {
                append(full.split(spot)[1])
            }
        }
    }

    @Composable
    fun makeString(text: String) : AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontSize = Typography.bodyMedium.fontSize
                )
            ) {
                append(text)
            }
        }
    }
}