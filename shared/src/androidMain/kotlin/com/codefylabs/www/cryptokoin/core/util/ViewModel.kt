package com.codefylabs.www.cryptokoin.core.util


import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel as AndroidXViewModel


actual abstract class ViewModel actual constructor() : AndroidXViewModel() {
    actual override fun onCleared() {
        super.onCleared()
    }

    actual val coroutine = viewModelScope
}


