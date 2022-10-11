package com.maf.slidingcomposable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.maf.slidingcomposable.ui.theme.SlidingComposableTheme
import com.osamaahmad.slidingpanel.SlideState
import com.osamaahmad.slidingpanel.SlideType
import com.osamaahmad.slidingpanel.SlidingLayout

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlidingComposableTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {

                    val slideState = remember {
                        mutableStateOf(SlideState())
                    }
                    SlidingLayout(
                        content = {
                            Content {
                                if (slideState.value.isShown()) {
                                    slideState.value.hide()
                                } else {
                                    slideState.value.showFull()
                                }
                            }
                        },
                        slidingContent = {
                            SlidingContent()
                        },
                        slideState = slideState,
                        onSlideBottom = {},
                        onSlideCenter = {},
                        onSlideFull = {},
                        heightFull = 600f
                    )

                }
            }
        }
    }
}

@Composable
fun Content(onClick: () -> Unit) {
    Column {
        Button(
            onClick = {
                onClick()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Show Slider")
        }
    }
}

@Composable
fun SlidingContent() {
    Column {

    }
}
