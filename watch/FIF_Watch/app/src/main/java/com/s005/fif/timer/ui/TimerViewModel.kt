package com.s005.fif.timer.ui

import android.content.Context
import androidx.compose.runtime.getValue
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val fifPreferenceModule: FIFPreferenceModule,
) : ViewModel() {
    var timerList by mutableStateOf(listOf<TimerInfo>())
    private var cTimerId = 1

    fun getTimerList() {
        viewModelScope.launch {
            val tempList = runBlocking {
                fifPreferenceModule.timerListFlow.first()
            }

            if (!tempList.isNullOrEmpty()) {
                timerList = tempList.map { it.toTimerInfo().update() }.sortedBy { it.timerId }
                cTimerId = tempList.last().timerId
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

        timerList = tempList
    }

    private fun stopTimer(timerInfo: TimerInfo) {
        val tempList = timerList.toMutableList()

        tempList.forEach {
            if (it.timerId == timerInfo.timerId) {
                AlarmUtil.cancel(timerInfo.timerId)
                it.isStart = false
            }
        }

        timerList = tempList
    }

    fun addTimer(time: Int, name: String) {
        val tempList = timerList.toMutableList()

        tempList.add(
            TimerInfo(
                cTimerId++,
                initTime = time,
                leftTime = time,
                isStart = false,
                timeMillis = System.currentTimeMillis(),
                title = name
            )
        )

        timerList = tempList
    }

    fun saveTimerListDataStore() {
        viewModelScope.launch {
            fifPreferenceModule.setTimerList(timerList.map { it.toTimerInfoEntity() })
        }
    }

    fun deleteTimerListDataStore() {
        viewModelScope.launch {
            fifPreferenceModule.removeTimerList()
        }
    }
}