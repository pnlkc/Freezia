package com.s005.fif.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class TTSUtil {
    private var tts: TextToSpeech? = null

    fun speak(context: Context, text: String) {
        tts = TextToSpeech(
            context
        ) {
            if (it != TextToSpeech.ERROR) {
                tts?.language = Locale.KOREAN
                tts?.setPitch(1.0f)
                tts?.setSpeechRate(1.0f)

                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    fun stop() {
        tts?.stop()
        tts?.shutdown()
    }
}