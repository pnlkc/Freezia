package com.s005.fif.timer.entity

import kotlinx.datetime.LocalTime
import kotlinx.datetime.serializers.LocalTimeIso8601Serializer
import kotlinx.datetime.toJavaLocalTime
import kotlinx.serialization.Serializable
import org.w3c.dom.Text

@Serializable
data class TimerInfoEntity(
    val timerId: Int,
    val initTime: Int,
    val leftTime: Int,
    val isStart: Boolean,
    val timeMillis: Long,
    val title: String,
    val step: Int
)

data class TimerInfo(
    val timerId: Int,
    val initTime: Int,
    var leftTime: Int,
    var isStart: Boolean,
    var timeMillis: Long,
    val title: String,
    val step: Int
)

fun TimerInfoEntity.toTimerInfo() = TimerInfo(
    timerId, initTime, leftTime, isStart, timeMillis, title, step
)

fun TimerInfo.toTimerInfoEntity() = TimerInfoEntity(
    timerId, initTime, leftTime, isStart, timeMillis, title, step
)