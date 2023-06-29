package com.production.gameplay.ui.screens

sealed class ScreensOlympus(val destination: String){
    object GameInfoScreen : ScreensOlympus("game_info_screen")
    object Playground : ScreensOlympus("playground")
    object FirstSreenMod : ScreensOlympus("first_screen_mod")
    object ScoreScreen : ScreensOlympus("score_screen")
    object LoadingScreen : ScreensOlympus("loading_screen")
}
