package com.production.gameplay.ui.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.production.gameplay.ContainerActivity
import com.production.gameplay.OlympusBase
import com.production.gameplay.R
import kotlinx.coroutines.delay


@Composable
fun LoadingScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences(OlympusBase.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    val data = sharedPref.getString(OlympusBase.SHARED_PREF_LINK, "no_data")

    Log.d("123123", "LoadingScreen")
    LaunchedEffect(key1 = "Next screen"){
        Log.d("123123", "Launch effect started")
        delay(2000)
        if (data=="dont_go"){
            navController.navigate(ScreensOlympus.FirstSreenMod.destination)
        } else {
            val intent = Intent(context, ContainerActivity::class.java)
            context.startActivity(intent)
        }
    }


    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.olympus_back),
            contentDescription = "back",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .align(Alignment.Center)
        )

    }

}