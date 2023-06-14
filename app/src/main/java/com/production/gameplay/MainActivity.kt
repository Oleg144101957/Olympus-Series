package com.production.gameplay

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.production.gameplay.ui.screens.NavigationMod
import com.production.gameplay.ui.screens.NavigationOlympus
import com.production.gameplay.ui.theme.OlympusSeriesTheme
import com.production.gameplay.utils.HumanDetector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val isHuman = HumanDetector.isHuman(this)
        super.onCreate(savedInstanceState)
        setContent {
            OlympusSeriesTheme {
                if (isHuman){
                    NavigationOlympus()
                } else {
                    NavigationMod()
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
        Log.d("123123", "GADID is: $gadid")
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

        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {

        }

        override fun onAttributionFailure(p0: String?) {

        }
    }

}

