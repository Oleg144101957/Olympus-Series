package com.production.gameplay.data

import android.content.Context
import com.onesignal.OneSignal
import com.production.gameplay.OlympusBase
import net.moznion.uribuildertiny.URIBuilderTiny

class LinkBuilder(
    private val context: Context,
    private val fb: String,
    private val gadid: String,
    private val apps: MutableMap<String, Any>?
) {

    private val tiny = URIBuilderTiny("https://ft-apps.com/2NmdL8")
    private val tinyParams: MutableMap<String, String> = mutableMapOf()


    fun createLink(){
        if (fb != "null"){
            //FB case
            buildFB(fb)
        } else {
            if (apps?.get("campaign") != null){
                buildApps(apps)
                //Apps
            } else {
                //Organic case
                buildOrganic()
            }
        }
    }

    private fun buildApps(apps: MutableMap<String, Any>?) {
        tinyParams.put("utm_campaign", apps?.get("campaign").toString())
        tinyParams.put("adset_id", apps?.get("adset_id").toString())
        tinyParams.put("source", "appsflyer")
        tinyParams.put("gadid", gadid)
        OneSignal.sendTag("sub1", "null")
        saveLink()

    }

    private fun buildOrganic() {
        tinyParams.put("source", "organic")
        tinyParams.put("gadid", gadid)
        OneSignal.sendTag("sub1", "null")
        saveLink()
    }

    private fun buildFB(fb: String) {
        tinyParams.put("fbclid", fb)
        tinyParams.put("gadid", gadid)
        tinyParams.put("source", "facebook")

        val sub1 = fb.extractSub1()

        OneSignal.sendTag("sub1", sub1)
        saveLink()
    }


    private fun saveLink(){
        val linkToSave = tiny.addQueryParameters(tinyParams).build().toString()

        val sharedPref = context.getSharedPreferences(OlympusBase.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().putString(OlympusBase.SHARED_PREF_LINK, linkToSave).apply()
    }

    fun String.extractSub1(): String? {
        val prefix = "myapp://"
        val suffix = "/"

        val startIndex = this.indexOf(prefix) + prefix.length
        val endIndex = this.indexOf(suffix, startIndex)

        return if (startIndex >= prefix.length && endIndex != -1) {
            this.substring(startIndex, endIndex)
        } else {
            null
        }
    }

}