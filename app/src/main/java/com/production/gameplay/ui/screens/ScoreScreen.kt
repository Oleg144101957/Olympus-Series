package com.production.gameplay.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Message
import android.util.Log
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.production.gameplay.OlympusBase

@Composable
fun ScoreScreen(navController: NavHostController, onBackPress: (web: WebView) -> Unit){
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences(OlympusBase.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    val link = sharedPref.getString(OlympusBase.SHARED_PREF_LINK, "")
    val time = sharedPref.getString(OlympusBase.SHARED_PREF_TIME, "")


    var webView: WebView? = null
    var fileDestinationsCallback: ValueCallback<Array<Uri>>? by remember {
        mutableStateOf(null)
    }
    val launcherForActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) {
        fileDestinationsCallback!!.onReceiveValue(it.toTypedArray())
    }

    Box(modifier = Modifier.fillMaxSize()){
        AndroidView(factory = { context: Context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true


                webViewClient = object : WebViewClient(){

                    override fun onPageFinished(view: WebView?, url: String) {
                        super.onPageFinished(view, url)

                        Log.d("123123", "The url is $url")

                        if(url.contains("https://first.ua/") && time != "client"){
                            sharedPref.edit().putString(OlympusBase.SHARED_PREF_LINK, url).apply()
                            sharedPref.edit().putString(OlympusBase.SHARED_PREF_TIME, "client").apply()
                        }
                        CookieManager.getInstance().flush()
                        Log.d("123123", "The url is $url")
                    }
                }

                webChromeClient = object : WebChromeClient(){


                    override fun onShowFileChooser(
                        webView: WebView?,
                        filePathCallback: ValueCallback<Array<Uri>>?,
                        fileChooserParams: FileChooserParams?
                    ): Boolean {
                        fileDestinationsCallback = filePathCallback
                        launcherForActivityResult.launch("image/*")
                        return true
                    }
                }



                loadUrl(link!!)
            }

        }, update = {
            webView = it
        })

        BackHandler(enabled = true) {
            onBackPress(webView!!)
        }
    }
}