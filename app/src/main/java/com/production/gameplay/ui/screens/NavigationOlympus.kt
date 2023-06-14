package com.production.gameplay.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationOlympus(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreensOlympus.FirstScreenHuman.destination
    ){
        composable(route = ScreensOlympus.FirstScreenHuman.destination){
            FirstOlympusScreen(navController)
        }
    }
}