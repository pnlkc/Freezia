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


    // TODO. 다국어 지원을 위해 하드 코딩된 문자열 처리 필요
    @Composable
    fun makeMainRecommendRecipeString(recommendType: Int, name: String) : AnnotatedString {
        when (recommendType) {
            0 -> { // 일반 레시피
                return buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = Typography.bodyMedium.fontSize
                        )
                    ) {
                        append("오늘은,\n")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = Typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(name)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = Typography.bodyMedium.fontSize
                        )
                    ) {
                        append(" 어떠신가요?")
                    }
                }
            }
            1 -> { // 스트레스 기반 레시피
                return buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = Typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("스트레스")
                    }


                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = Typography.bodyMedium.fontSize
                        )
                    ) {
                        append("가 높으신 날,\n")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = Typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(name)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = Typography.bodyMedium.fontSize
                        )
                    ) {
                        append(" 어떠신가요?")
                    }
                }
            }
            2 -> { // 수면 시간 기반 레시피
                return buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = Typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("수면")
                    }


                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = Typography.bodyMedium.fontSize
                        )
                    ) {
                        append("이 부족하신 오늘,\n")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = Typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(name)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = Typography.bodyMedium.fontSize
                        )
                    ) {
                        append(" 어떠신가요?")
                    }
                }
            }
            3 -> { // 혈중산소 기반 레시피
                return buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = Typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("혈중 산소 포화도")
                    }


                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = Typography.bodyMedium.fontSize
                        )
                    ) {
                        append("를 위한,\n")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = Typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(name)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = Typography.bodyMedium.fontSize
                        )
                    ) {
                        append(" 어떠신가요?")
                    }
                }
            }
            else -> { // 유통기한 임박 재료 기반 레시피
                return buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = Typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("유통기한")
                    }


                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = Typography.bodyMedium.fontSize
                        )
                    ) {
                        append("이 임박한 재료를 활용한\n")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = Typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(name)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = Typography.bodyMedium.fontSize
                        )
                    ) {
                        append(" 어떠신가요?")
                    }
                }
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