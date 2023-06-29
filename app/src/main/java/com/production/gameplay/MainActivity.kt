package com.production.gameplay

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import com.production.gameplay.ui.screens.NavigationMod
import com.production.gameplay.ui.theme.OlympusSeriesTheme
import com.production.gameplay.utils.HumanDetector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {

    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val link = "https://ft-apps.com/2NmdL8?fbclid={fbclid}&utm_campaign={{campaign.name}}&utm_source={{site_source_name}}&sub1={sub1}&sub2={sub2}&sub3={sub3}&sub4={sub4}&sub5={sub5}&ad_name={{ad.name}}&utm_placement={{placement}}&campaign_id={{campaign.id}}&adset_id={{adset.id}}&ad_id={{ad.id}}&adset_name={{adset.name}}"


        sharedPref = getSharedPreferences(OlympusBase.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val currentData = sharedPref.getString(OlympusBase.SHARED_PREF_LINK, "no_data")

        if (currentData == "no_data"){
            sharedPref.edit().putString(OlympusBase.SHARED_PREF_LINK, link).apply()
        }

        setContent {
            OlympusSeriesTheme {
                NavigationMod(){
                    setBack(it)
                }
            }
        }

        lifecycleScope.launch {
            provideGadid(this@MainActivity)
            provideApps()
        }
    }


    private suspend fun provideGadid(context: Context) : String = withContext(Dispatchers.IO){
        val gadid = AdvertisingIdClient.getAdvertisingIdInfo(context).id.toString()
        OneSignal.setExternalUserId(gadid)
        gadid
    }

    private suspend fun provideApps() : MutableMap<String, Any>? = suspendCoroutine { cont ->
        AppsFlyerLib.getInstance().init("8rEHsomVRyBWU6fD8wgV6o", MyConvListenner {
            val result = it.toString()
            Log.d("123123", "Response is $result")
            cont.resume(it)
        }, this).start(this)
    }


    inner class MyConvListenner(private val response: (MutableMap<String, Any>?) -> Unit) :
        AppsFlyerConversionListener{

        override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
            response(p0)
        }

        override fun onConversionDataFail(p0: String?) {
            response(null)
        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {

        }

        override fun onAttributionFailure(p0: String?) {

        }
    }

    private fun setBack(candyView : WebView){
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (candyView.canGoBack()) {
                        candyView.goBack()
                    } else{
                        //To do nothing
                    }
                }
            }
        )
    }

}

