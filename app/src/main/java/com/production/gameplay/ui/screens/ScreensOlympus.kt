package com.production.gameplay.ui.screens

sealed class ScreensOlympus(val destination: String){
    object FirstScreenHuman : ScreensOlympus("first_screen")
    object RulesScreen : ScreensOlympus("rules_screen")
    object GameInfoScreen : ScreensOlympus("game_info_screen")
    object Playground : ScreensOlympus("playground")
    object FirstSreenMod : ScreensOlympus("first_screen_mod")
}
