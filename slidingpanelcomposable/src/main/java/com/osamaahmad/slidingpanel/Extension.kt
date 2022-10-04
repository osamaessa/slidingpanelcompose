package com.osamaahmad.slidingpanel

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Dp.px(): Float = with(LocalDensity.current) {
    this@px.toPx()
}

@Composable
fun Int.px(): Float = with(LocalDensity.current) {
    this@px.dp.toPx()
}

