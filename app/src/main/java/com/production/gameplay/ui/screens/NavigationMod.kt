package com.production.gameplay.ui.screens

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationMod(onBackPress: (web: WebView) -> Unit) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreensOlympus.LoadingScreen.destination
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
    }
}