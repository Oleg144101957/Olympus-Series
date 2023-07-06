package com.production.gameplay.ui.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.production.gameplay.ContainerActivity
import com.production.gameplay.OlympusBase

@Composable
fun NavigationMod() {

    val navController = rememberNavController()

    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences(OlympusBase.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    val link = sharedPref.getString(OlympusBase.SHARED_PREF_LINK, "no_data")

    Log.d("123123", "The link in Navigation is $link")

    val destination: String?

    when (link) {
        "dont_go" -> {
            destination = ScreensOlympus.FirstSreenMod.destination
        }
        "no_data" -> {
            destination = ScreensOlympus.LoadingScreen.destination
        }
        else -> {
            destination = ScreensOlympus.DirectToFirst.destination
        }
    }



    NavHost(
        navController = navController,
        startDestination = destination
    ){

        composable(route = ScreensOlympus.LoadingScreen.destination){
            LoadingScreen(navController)
        }

        composable(route = ScreensOlympus.FirstSreenMod.destination){
            FirstScreenMod(navController)
        }

        composable(route = ScreensOlympus.Playground.destination){
            Playground()
        }

        composable(route = ScreensOlympus.GameInfoScreen.destination){
            HelpScreen()
        }

        composable(route = ScreensOlympus.DirectToFirst.destination){
            val intent = Intent(context, ContainerActivity::class.java)
            context.startActivity(intent)
        }
    }
}