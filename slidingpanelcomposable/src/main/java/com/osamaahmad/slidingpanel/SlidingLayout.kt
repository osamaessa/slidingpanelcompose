package com.osamaahmad.slidingpanel

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.animate
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SlidingLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    slidingContent: @Composable () -> Unit,
    slideState: MutableState<SlideState>,
    heightBottom: Float = 100f,
    heightCenter: Float = LocalConfiguration.current.screenHeightDp / 2f,
    heightFull: Float = LocalConfiguration.current.screenHeightDp.toFloat(),
    onSlideBottom: () -> Unit,
    onSlideCenter: () -> Unit,
    onSlideFull: () -> Unit,
) {
    var showScrollable by remember { mutableStateOf(true) }
    val density = LocalDensity.current.density

    val currentHeight = remember {
        mutableStateOf(heightBottom)
    }

    showScrollable = slideState.value.slideType() != SlideType.None

    if (slideState.value.slideType() == SlideType.Full){
        currentHeight.value = heightFull
    }else if (slideState.value.slideType() == SlideType.Center){
        currentHeight.value = heightCenter
    }else if (slideState.value.slideType() == SlideType.Bottom){
        currentHeight.value = heightBottom
    }
    Box(modifier = modifier) {
        content()


        Log.d("showScrollable", "showScrollable: $showScrollable")
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            AnimatedVisibility(
                visible = showScrollable,
                enter = slideInVertically {
                    (currentHeight.value.toInt() * density).toInt()
                },
                exit = slideOutVertically {
                    (currentHeight.value.toInt() * density).toInt()
                }
            ) {
                SlidingContent(
                    slideState = slideState,
                    onSlideBottom = { onSlideBottom() },
                    onSlideCenter = { onSlideCenter() },
                    onSlideFull = { onSlideFull() },
                    slidingContent = slidingContent,
                    heightBottom = heightBottom,
                    heightCenter = heightCenter,
                    heightFull = heightFull,
                    currentHeight = currentHeight
                )
            }
        }

    }

}

@Composable
private fun SlidingContent(
    modifier: Modifier = Modifier,
    onSlideBottom: () -> Unit,
    onSlideCenter: () -> Unit,
    onSlideFull: () -> Unit,
    slidingContent: @Composable () -> Unit,
    slideState: MutableState<SlideState>,
    heightBottom: Float = 100f,
    heightCenter: Float = LocalConfiguration.current.screenHeightDp / 2f,
    heightFull: Float = LocalConfiguration.current.screenHeightDp.toFloat(),
    currentHeight: MutableState<Float>,
) {

    val deviceHeight = LocalConfiguration.current.screenHeightDp

    val density = LocalDensity.current.density
    val fullMinHeight = heightFull / 4f
    val bottomMax = deviceHeight / 4f

    var offsetY by remember { mutableStateOf(0f) }

    Column(
        modifier = modifier
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    offsetY += delta
                    currentHeight.value += (delta / density) * -1

                },
                onDragStopped = {
                    if ((heightFull - currentHeight.value) <= fullMinHeight) {
                        slideState.value.showFull()
                        onSlideFull()
                        launch {
                            animate(
                                initialValue = currentHeight.value,
                                targetValue = heightFull,
                                initialVelocity = 0.5f
                            ) { value, _ ->
                                currentHeight.value = value
                            }
                        }
                    } else if (currentHeight.value <= bottomMax) {
                        slideState.value.showBottom()
                        onSlideBottom()
                        launch {
                            animate(
                                initialValue = currentHeight.value,
                                targetValue = heightBottom,
                                initialVelocity = 0.5f
                            ) { value, _ ->
                                currentHeight.value = value
                            }
                        }
                    } else {
                        slideState.value.showCenter()
                        onSlideCenter()
                        launch {
                            animate(
                                initialValue = currentHeight.value,
                                targetValue = heightCenter,
                                initialVelocity = 0.5f
                            ) { value, _ ->
                                currentHeight.value = value
                            }
                        }
                    }
                    offsetY = 0f

                }
            )
            .fillMaxWidth()
            .height(currentHeight.value.dp)
            .background(Color.Black)
    ) {
        slidingContent()
    }
}