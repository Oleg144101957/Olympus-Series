package com.production.gameplay.ui.screens

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.production.gameplay.OlympusBase
import com.production.gameplay.R
import com.production.gameplay.utils.HumanDetector
import com.production.gameplay.utils.OlympusManager
import com.production.gameplay.utils.OlympusWebClient
import kotlinx.coroutines.delay

@Composable
fun FirstOlympusScreen(navController: NavHostController){
    Box(modifier = Modifier
        .fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.olympus_design),
            contentDescription = "back",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )

        ShowScoreData(navController)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ShowScoreData(navController: NavHostController){
    val olympusManager = OlympusManager()
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    val isVisibleProgress = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = "hide_progress"){
        delay(4000)
        isVisibleProgress.value = false
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {

        AndroidView(factory = {context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true
                settings.userAgentString = olympusManager.olympusAgent(settings.userAgentString)
                webViewClient = OlympusWebClient()
                webChromeClient = WebChromeClient()// ToDo

                loadUrl("https://first.ua/ua")

            }
        })

        if (isVisibleProgress.value){
            Box(modifier = Modifier
                .fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.olympus_design),
                    contentDescription = "black back",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                )

                LottieAnimation(
                    composition = composition,
                    progress = progress,
                    modifier = Modifier
                        .size(400.dp)
                )
            }
        }
    }
}