package com.production.gameplay.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.production.gameplay.OlympusBase
import com.production.gameplay.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun FirstScreenMod(navController: NavHostController) {
    val mainAnim = remember {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(key1 = "mainAnim"){
        mainAnim.animateTo(
            targetValue = 0.45f,
            animationSpec = infiniteRepeatable(
                tween(2500, 100, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.olympus_back),
            contentDescription = "back",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()

        )

        Image(
            painter = painterResource(id = R.drawable.god_olympus),
            contentDescription = "Olympus God",
            alpha = mainAnim.value,
            modifier = Modifier
                .offset(x = 32.dp)
                .align(Alignment.Center)
        )


        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ){

                Image(
                    painter = painterResource(id = R.drawable.btn),
                    contentDescription = "Button play",
                    alpha = 0.8f,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            navController.navigate(ScreensOlympus.Playground.destination)
                        }
                )

                Text(
                    text = "Play Game",
                    fontFamily = OlympusBase.olympusFont,
                    color = Color.White,
                    fontSize = 38.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(0.8f)
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ){

                Image(
                    painter = painterResource(id = R.drawable.btn),
                    contentDescription = "Button play",
                    alpha = 0.8f,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            navController.navigate(ScreensOlympus.GameInfoScreen.destination)
                        }
                )

                Text(
                    text = "Help",
                    fontFamily = OlympusBase.olympusFont,
                    color = Color.White,
                    fontSize = 38.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(0.8f)
                )
            }
        }
    }
}