package com.production.gameplay

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

class OlympusBase {
    companion object{
        const val DB_NAME = "destination_db"
        val olympusFont = FontFamily(Font(R.font.olymp))
        val SHARED_PREF_NAME = "shared_pref"
        val SHARED_PREF_LINK = "shared_pref_link"
        val SHARED_PREF_TIME = "shared_pref_time"
    }
}