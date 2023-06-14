package com.production.gameplay.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationMod() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreensOlympus.FirstSreenMod.destination
    ){

        composable(route = ScreensOlympus.FirstSreenMod.destination){
            FirstScreenMod(navController)
        }

        composable(route = ScreensOlympus.Playground.destination){
            Playground()
        }

    }
}