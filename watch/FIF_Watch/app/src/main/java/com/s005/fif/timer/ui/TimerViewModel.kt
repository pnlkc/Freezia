package com.s005.fif.timer.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s005.fif.di.FIFPreferenceModule
import com.s005.fif.timer.entity.TimerInfo
import com.s005.fif.timer.entity.toTimerInfo
import com.s005.fif.timer.entity.toTimerInfoEntity
import com.s005.fif.utils.AlarmUtil
import com.s005.fif.utils.TimeUtil.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val fifPreferenceModule: FIFPreferenceModule,
) : ViewModel() {
    var timerList = mutableStateListOf<TimerInfo>()
    private var cTimerId = 1

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                updateTime()
            }
        }
    }

    fun getTimerList() {
        viewModelScope.launch {
            val tempList = runBlocking {
                fifPreferenceModule.timerListFlow.first()
            }

            if (!tempList.isNullOrEmpty()) {
                timerList.clear()
                timerList.addAll(tempList.map { it.toTimerInfo().update() }.sortedBy { it.timerId })
                cTimerId = tempList.last().timerId + 1
            }
        }
    }

    fun timerBtnClicked(isStart: Boolean, context: Context, timerInfo: TimerInfo) {
        if (isStart) {
            startTimer(context, timerInfo)
        } else {
            stopTimer(timerInfo)
        }
    }

    private fun startTimer(context: Context, timerInfo: TimerInfo) {
        val tempList = timerList.toMutableList()

        tempList.forEach {
            if (it.timerId == timerInfo.timerId) {
                AlarmUtil.setAlarm(context, it.leftTime * 1000L, timerInfo.timerId, timerInfo.title, timerInfo.initTime)
                it.timeMillis = System.currentTimeMillis()
                it.isStart = true
            }
        }

        timerList.clear()
        timerList.addAll(tempList)
    }

    private fun stopTimer(timerInfo: TimerInfo) {
        val tempList = timerList.toMutableList()

        tempList.forEach {
            if (it.timerId == timerInfo.timerId) {
                AlarmUtil.cancel(timerInfo.timerId)
                it.isStart = false
            }
        }

        timerList.clear()
        timerList.addAll(tempList)
    }

    fun addTimer(time: Int, name: String, step: Int): Int {
        timerList.add(
            TimerInfo(
                cTimerId++,
                initTime = if (step > 2) 7 else time / 60,
                leftTime = if (step > 2) 7 else time / 60,
                isStart = false,
                timeMillis = System.currentTimeMillis(),
                title = name,
                step = step
            )
        )

        return timerList.size
    }

    fun saveTimerListDataStore() {
        viewModelScope.launch {
            fifPreferenceModule.setTimerList(timerList.map { it.toTimerInfoEntity() })
        }
    }

    fun deleteTimerListDataStore() {
        viewModelScope.launch {
            timerList.clear()
            fifPreferenceModule.removeTimerList()
        }
    }

    fun updateTime() {
        val tempList = timerList.toList()
        timerList.clear()

        tempList.forEach {
            if (it.isStart) {
                val newLeftTime = maxOf(0, it.leftTime - 1)

                if (newLeftTime == 0) {
                    timerList.add(
                        it.copy(
                            leftTime = it.initTime,
                            isStart = false
                        )
                    )
                } else {
                    timerList.add(it.copy(leftTime = newLeftTime))
                }
            } else {
                timerList.add(it)
            }
        }
    }

    fun resetTimer(timerInfo: TimerInfo) {
        stopTimer(timerInfo)

        val tempList = timerList.toList()
        timerList.clear()

        tempList.forEach {
            if (it.timerId == timerInfo.timerId) {
                timerList.add(it.copy(leftTime = it.initTime, isStart = false))
            } else {
                timerList.add(it)
            }
        }
    }
}