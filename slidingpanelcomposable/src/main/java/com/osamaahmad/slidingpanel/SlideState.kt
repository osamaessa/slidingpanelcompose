package com.osamaahmad.slidingpanel

import androidx.compose.runtime.mutableStateOf

class SlideState {

    private val state = mutableStateOf(SlideType.None)


    fun showBottom() {
        state.value = SlideType.Bottom
    }

    fun showCenter() {
        state.value = SlideType.Center
    }

    fun showFull() {
        state.value = SlideType.Full
    }

    fun hide() {
        state.value = SlideType.None
    }

    fun slideType() = state.value

    fun isShown() = state.value != SlideType.None
}