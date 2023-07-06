package com.production.gameplay

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import com.production.gameplay.data.LinkBuilder
import com.production.gameplay.ui.screens.NavigationMod
import com.production.gameplay.ui.theme.OlympusSeriesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {

    private lateinit var sharedPref: SharedPreferences

    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        //do some work
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                //do some work
            } else requestPermissionLauncher.launch(permission)
        } else {
            //do some work
        }


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                )



        sharedPref = getSharedPreferences(OlympusBase.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val currentData = sharedPref.getString(OlympusBase.SHARED_PREF_LINK, "no_data")

        if (currentData == "no_data"){
            checkLink()
        }


        setContent {
            OlympusSeriesTheme {
                NavigationMod()
            }
        }


    }

    private fun checkLink(){
        val context = this

        lifecycleScope.launch {
            val gadid = provideGadid(this@MainActivity)
            val apps = provideApps()
            val facebook = provideFacebook()

            val linkBuilder = LinkBuilder(
                context = context,
                fb = facebook,
                gadid = gadid,
                apps = apps
            )

            linkBuilder.createLink()

            Log.d("123123", "gadid is $gadid")
            Log.d("123123", "apps is ${apps.toString()}")
            Log.d("123123", "facebook is $facebook")

        }
    }


    private suspend fun provideGadid(context: Context) : String = withContext(Dispatchers.IO){
        val gadid = AdvertisingIdClient.getAdvertisingIdInfo(context).id.toString()
        OneSignal.setExternalUserId(gadid)
        gadid
    }

    private suspend fun provideFacebook() : String = suspendCoroutine{ cont ->
        AppLinkData.fetchDeferredAppLinkData(this){
            cont.resume(it?.targetUri.toString())
        }
    }

    private suspend fun provideApps() : MutableMap<String, Any>? = suspendCoroutine { cont ->
        AppsFlyerLib.getInstance().init("8rEHsomVRyBWU6fD8wgV6o", MyConvListenner {
            val result = it.toString()
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

    override fun onBackPressed() {

    }

}

