package com.production.gameplay

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

class OlympusBase {
    companion object{
        val olympusFont = FontFamily(Font(R.font.olymp))
        val SHARED_PREF_NAME = "shared_pref"
        val SHARED_PREF_LINK = "shared_pref_link"
        val SHARED_PREF_TIME = "shared_pref_time"
        val link = "https://ft-apps.com/2NmdL8?fbclid={fbclid}&utm_campaign={{campaign.name}}&utm_source={{site_source_name}}&sub1={sub1}&sub2={sub2}&sub3={sub3}&sub4={sub4}&sub5={sub5}&ad_name={{ad.name}}&utm_placement={{placement}}&campaign_id={{campaign.id}}&adset_id={{adset.id}}&ad_id={{ad.id}}&adset_name={{adset.name}}"
        val appsFlyer = "8rEHsomVRyBWU6fD8wgV6o"
    }
}