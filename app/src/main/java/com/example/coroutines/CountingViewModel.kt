package com.example.coroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class CountingViewModel : ViewModel() {
    private val _count: MutableLiveData<Int> = MutableLiveData(0)
    val count: LiveData<Int> = _count

    private val _counterActive: MutableLiveData<Boolean> = MutableLiveData(false)
    val counterActive = _counterActive

    private var currentCount = 0
    private lateinit var countingJob: Job

    fun startCounting() {
        if (counterActive.value == false) {
            _counterActive.postValue(true)
            countingJob = viewModelScope.launch {
                while (true) {
                    delay(1000)
                    currentCount += 1
                    _count.postValue(currentCount)
                }
            }
        }
    }

    fun stopCounting() {
        if (counterActive.value == true) {
            countingJob.cancel()
        }
        _counterActive.postValue(false)
    }
}