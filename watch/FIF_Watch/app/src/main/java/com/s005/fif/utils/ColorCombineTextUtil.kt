package com.s005.fif.utils

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.s005.fif.R
import com.s005.fif.ui.theme.fifWatchColorPalette

object ColorCombineTextUtil {
    fun makeCookTimeText(time: Int, context: Context): AnnotatedString {
        val text = context.getString(
            R.string.cook_time_text,
            time,
        )

        val start = text.indexOf(time.toString())
        val end = start + time.toString().lastIndex

        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append(text.slice(0 until start))
            }
            withStyle(style = SpanStyle(color = fifWatchColorPalette.primary)) {
                append(text.slice(start..end))
            }
            withStyle(style = SpanStyle(color = Color.White)) {
                append(text.slice(end + 1..text.lastIndex))
            }
        }

        return annotatedString
    }

//    fun makeWarningText(ingredient: String, fullText: String): AnnotatedString {
//        val start = fullText.indexOf(ingredient)
//        val end = start + ingredient.lastIndex
//
//        val annotatedString = buildAnnotatedString {
//            withStyle(style = SpanStyle(color = Color.White)) {
//                append(fullText.slice(0 until start))
//            }
//            withStyle(style = SpanStyle(color = fifWatchColorPalette.primary)) {
//                append(fullText.slice(start..end))
//            }
//            withStyle(style = SpanStyle(color = Color.White)) {
//                append(fullText.slice(end+ 1..fullText.lastIndex))
//            }
//        }
//
//        return annotatedString
//    }

    fun makeWarningText(ingredient: String, disease: String, fullText: String): AnnotatedString {
        val startOne = fullText.indexOf(ingredient)
        val endOne = startOne + ingredient.lastIndex
        val startTwo = fullText.indexOf(disease)
        val endTwo = startTwo + disease.lastIndex

        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append(fullText.slice(0 until startOne))
            }
            withStyle(style = SpanStyle(color = fifWatchColorPalette.primary)) {
                append(fullText.slice(startOne..endOne))
            }
            withStyle(style = SpanStyle(color = Color.White)) {
                append(fullText.slice(endOne + 1 until startTwo))
            }
            withStyle(style = SpanStyle(color = fifWatchColorPalette.primary)) {
                append(fullText.slice(startTwo..endTwo))
            }
            withStyle(style = SpanStyle(color = Color.White)) {
                append(fullText.slice(endTwo + 1..fullText.lastIndex))
            }
        }

        return annotatedString
    }

    fun makeTimerDoneText(timerName: String, context: Context): AnnotatedString {
        val text = context.getString(
            R.string.timer_done_text,
            timerName
        )

        val start = text.indexOf(timerName)
        val end = start + timerName.lastIndex

        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(color = fifWatchColorPalette.primary)) {
                append(text.slice(start..end))
            }
            withStyle(style = SpanStyle(color = Color.White)) {
                append(text.slice(end + 1..text.lastIndex))
            }
        }

        return annotatedString
    }
}