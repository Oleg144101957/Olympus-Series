package com.production.gameplay.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.production.gameplay.OlympusBase
import com.production.gameplay.R
import kotlinx.coroutines.delay

@Composable
fun Playground() {

    val screenOlympusHeight = LocalConfiguration.current.screenHeightDp

    val tips = remember{
        mutableStateOf(0)
    }

    //Text before start
    val isPushTwice = remember {
        mutableStateOf(false)
    }

    val fallingSpeed = remember {
        mutableStateOf(3500)
    }

    val ani1 = remember {
        Animatable(initialValue = 0f)
    }

    val ani2 = remember {
        Animatable(initialValue = 0f)
    }

    val ani3 = remember {
        Animatable(initialValue = 0f)
    }

    val ani4 = remember {
        Animatable(initialValue = 0f)
    }

    val ani5 = remember {
        Animatable(initialValue = 0f)
    }

    val ani6 = remember {
        Animatable(initialValue = 0f)
    }


    val isVisible1 = remember {
        mutableStateOf(true)
    }

    val isVisible2 = remember {
        mutableStateOf(true)
    }

    val isVisible3 = remember {
        mutableStateOf(true)
    }

    val isVisible4 = remember {
        mutableStateOf(true)
    }

    val isVisible5 = remember {
        mutableStateOf(true)
    }

    val isVisible6 = remember {
        mutableStateOf(true)
    }



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

        Image(
            painter = painterResource(id = R.drawable.olympus_back),
            contentDescription = "back",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.artifact28),
            contentDescription = "olympus beam",
            alpha = 0.25f
        )


        //Falling objects surface
        Box(modifier = Modifier
            .fillMaxHeight()
            .width(250.dp)){

            if (isVisible1.value){
                Image(
                    painter = painterResource(id = R.drawable.artifact21),
                    contentDescription = "art1",
                    modifier = Modifier
                        .offset(y = ani1.value.dp, x = 33.dp)
                        .align(Alignment.TopCenter)
                        .clickable {
                            tips.value += 1
                            isVisible1.value = false
                        }
                )
            }


            if (isVisible2.value){
                Image(
                    painter = painterResource(id = R.drawable.artifact22),
                    contentDescription = "art2",
                    modifier = Modifier
                        .offset(y = ani2.value.dp, x = (-33).dp)
                        .align(Alignment.TopCenter)
                        .clickable {
                            tips.value += 1
                            isVisible2.value = false
                        }
                )
            }

            if (isVisible3.value){
                Image(
                    painter = painterResource(id = R.drawable.artifact23),
                    contentDescription = "art3",
                    modifier = Modifier
                        .size(80.dp)
                        .offset(y = ani3.value.dp, x = (-15).dp)
                        .align(Alignment.TopCenter)
                        .clickable {
                            tips.value += 1
                            isVisible3.value = false
                        }
                )
            }

            if (isVisible4.value){
                Image(
                    painter = painterResource(id = R.drawable.artifact24),
                    contentDescription = "art4",
                    modifier = Modifier
                        .size(80.dp)
                        .offset(y = ani4.value.dp, x = (-45).dp)
                        .align(Alignment.TopCenter)
                        .clickable {
                            tips.value += 1
                            isVisible4.value = false
                        }
                )
            }

            if (isVisible5.value){
                Image(
                    painter = painterResource(id = R.drawable.artifact25),
                    contentDescription = "art5",
                    modifier = Modifier
                        .size(85.dp)
                        .offset(y = ani5.value.dp, x = (-40).dp)
                        .align(Alignment.TopCenter)
                        .clickable {
                            tips.value += 1
                            isVisible5.value = false
                        }
                )
            }

            if (isVisible6.value){
                Image(
                    painter = painterResource(id = R.drawable.artifact26),
                    contentDescription = "art6",
                    modifier = Modifier
                        .size(85.dp)
                        .offset(y = ani6.value.dp, x = 40.dp)
                        .align(Alignment.TopCenter)
                        .clickable {
                            tips.value += 1
                            isVisible6.value = false
                        }
                )
            }

            LaunchedEffect(key1 = "anim1"){
                isPushTwice.value = true
                delay(2500)
                isPushTwice.value = false

                ani1.animateTo(
                    targetValue = screenOlympusHeight.toFloat(),
                    animationSpec = infiniteRepeatable(
                        tween(fallingSpeed.value, delayMillis = 150, easing = FastOutLinearInEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }

            LaunchedEffect(key1 = "anim2"){
                delay(2500)

                ani2.animateTo(
                    targetValue = screenOlympusHeight.toFloat(),
                    animationSpec = infiniteRepeatable(
                        tween(fallingSpeed.value, delayMillis = 290, easing = FastOutLinearInEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }


            LaunchedEffect(key1 = "anim3"){
                delay(2500)

                ani3.animateTo(
                    targetValue = screenOlympusHeight.toFloat(),
                    animationSpec = infiniteRepeatable(
                        tween(fallingSpeed.value, delayMillis = 700, easing = FastOutLinearInEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }


            LaunchedEffect(key1 = "anim4"){
                delay(2500)

                ani4.animateTo(
                    targetValue = screenOlympusHeight.toFloat(),
                    animationSpec = infiniteRepeatable(
                        tween(fallingSpeed.value, delayMillis = 1500, easing = FastOutLinearInEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }

            LaunchedEffect(key1 = "anim5"){
                delay(2500)

                ani5.animateTo(
                    targetValue = screenOlympusHeight.toFloat(),
                    animationSpec = infiniteRepeatable(
                        tween(fallingSpeed.value, delayMillis = 100, easing = FastOutLinearInEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }

            LaunchedEffect(key1 = "anim6"){
                delay(2500)

                ani6.animateTo(
                    targetValue = screenOlympusHeight.toFloat(),
                    animationSpec = infiniteRepeatable(
                        tween(fallingSpeed.value, delayMillis = 2000, easing = FastOutLinearInEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }

            LaunchedEffect(key1 = "show falling"){

                delay(2750)

                repeat(8){
                    delay(5000)
                    isVisible1.value = true
                    isVisible2.value = true
                    isVisible3.value = true
                    isVisible4.value = true
                    isVisible5.value = true
                    fallingSpeed.value = fallingSpeed.value-700
                }
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .align(Alignment.TopCenter)
        ){

            Image(
                painter = painterResource(id = R.drawable.btn),
                contentDescription = "score background"
            )

            Text(
                text = "You have ${tips.value} points",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = OlympusBase.olympusFont
            )

        }

        Image(
            painter = painterResource(id = R.drawable.base),
            contentDescription = "Base",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 40.dp)
        )
    }
}

@Preview
@Composable
fun PlaygroundPreview(){
    Playground()
}