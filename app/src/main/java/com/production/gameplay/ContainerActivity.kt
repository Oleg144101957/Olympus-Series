package com.production.gameplay

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Message
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.production.gameplay.databinding.ActivityContainerBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class ContainerActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: ActivityContainerBinding
    private var photoModule: PhotoModule? = null
    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            //do some work
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = getSharedPreferences(OlympusBase.SHARED_PREF_NAME, Context.MODE_PRIVATE)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                //do some work
            } else requestPermissionLauncher.launch(permission)
        } else {
            //do some work
        }



        Log.d("123123", "onCreate")
        val destination = sharedPref.getString(OlympusBase.SHARED_PREF_LINK, "dont_go")
        setWebView(binding.webContainer, destination)
    }

    private fun setWebView(webContainer: WebView, url: String?) {
        webContainer.apply {
            if (url != null){
                webContainer.loadUrl(url)
            }

            isSaveEnabled = true
            isFocusableInTouchMode = true
            CookieManager.getInstance().setAcceptCookie(true)
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)


            settings.apply {
                setRenderPriority(WebSettings.RenderPriority.HIGH)
                allowContentAccess = true
                useWideViewPort = true
                mediaPlaybackRequiresUserGesture = true
                pluginState = WebSettings.PluginState.ON
                cacheMode = WebSettings.LOAD_NORMAL
                loadsImagesAutomatically = true
                mixedContentMode = 0
                setEnableSmoothTransition(true)
                databaseEnabled = true
                savePassword = true
                allowUniversalAccessFromFileURLs = true
                saveFormData = true
                allowFileAccess = true
                javaScriptCanOpenWindowsAutomatically = true
                allowFileAccessFromFileURLs = true
                setSupportMultipleWindows(true)
                loadWithOverviewMode = true
                domStorageEnabled = true
                setJavaScriptEnabled(true)
                userAgentString = userAgentString.replace("; wv", "")
            }

            webViewClient = this@ContainerActivity.getWebViewClient()
            webChromeClient = this@ContainerActivity.getWebChromeClient()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle = Bundle()
        binding.webContainer.saveState(bundle)
        outState.putBundle("myWeb", bundle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.webContainer.restoreState(savedInstanceState)
    }

    private fun getWebChromeClient(): WebChromeClient {
        return object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                photoModule?.onReceivedPathCallBack(filePathCallback)
                return true
            }

            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {

                val newWebView = WebView(this@ContainerActivity)
                setWebView(newWebView, null)
                binding.root.addView(newWebView)
                val transport = resultMsg?.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()

                return true
            }

            override fun onCloseWindow(window: WebView?) {
                super.onCloseWindow(window)
                binding.root.removeView(window)
            }
        }
    }

    private fun getWebViewClient(): WebViewClient {
        return object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                val currentData = sharedPref.getString(OlympusBase.SHARED_PREF_LINK, "no_data")

                Log.d("123123", "The URL is $url")

                if (url != null && url.contains("first")){
                    sharedPref.edit().putString(OlympusBase.SHARED_PREF_LINK, "https://first.ua/").apply()
                }

                lifecycleScope.launch {
                    CookieManager.getInstance().flush()
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("123123", "The url is $url")
                lifecycleScope.launch {
                    CookieManager.getInstance().flush()
                    delay(1000)
                    view?.visibility = View.VISIBLE
                }
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse
            ) {
                when(errorResponse.statusCode){
                    404, 403 -> {
                        sharedPref.edit().putString(OlympusBase.SHARED_PREF_LINK, "dont_go").apply()
                        startActivity(Intent(this@ContainerActivity, MainActivity::class.java))
                    }
                }
            }
        }
    }


    class PhotoModule(
        private val context: AppCompatActivity
    ) {
        private var photoPath: String? = null
        private var filePath: ValueCallback<Array<Uri>>? = null

        private val resultLaucher = context.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            onReceivedResult(result)
        }

        private val permissionsRequested = context.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { results ->
            results?.let { startRequesting(it.values.firstOrNull() ?: false) }
        }

        fun onReceivedPathCallBack(
            filePathCallback: ValueCallback<Array<Uri>>?
        ) {

            filePath?.onReceiveValue(null)
            filePath = filePathCallback

            val permissions = arrayOf(
                android.Manifest.permission.CAMERA,
            )
            val hasPermissions = ContextCompat.checkSelfPermission(
                context, permissions[0]
            ) == PackageManager.PERMISSION_GRANTED

            if (hasPermissions) {
                startRequesting(true)
            } else permissionsRequested.launch(permissions)
        }


        private fun startRequesting(cameraGranted: Boolean) {
            var pictureIntent: Intent? = null
            if (cameraGranted) {
                var photoFile: File? = null
                pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    photoFile = createPictureFromCamera()
                    pictureIntent.putExtra("PhotoPath", photoPath)
                } catch (e: IOException) {

                }
                if (photoFile != null) {
                    photoPath = "file:${photoFile.absolutePath}"
                    val uri = FileProvider.getUriForFile(
                        context, "${context.packageName}.provider", photoFile
                    )
                    pictureIntent.apply {
                        putExtra(MediaStore.EXTRA_OUTPUT, uri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                } else {
                    pictureIntent = null
                }
            }

            val contentIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }

            val intentArray: Array<Intent?> = pictureIntent?.let {
                arrayOf(it)
            } ?: arrayOfNulls(0)

            Intent(Intent.ACTION_CHOOSER).run {
                putExtra(Intent.EXTRA_TITLE, "Image Chooser")
                putExtra(Intent.EXTRA_INTENT, contentIntent)
                putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
                resultLaucher.launch(this)
            }
        }

        private fun onReceivedResult(result: ActivityResult?) {
            var results: Array<Uri>? = null
            if (result?.resultCode == Activity.RESULT_OK) {
                if (filePath == null) return

                if (result.data == null || result.data?.data == null) {
                    if (photoPath != null) {
                        results = arrayOf(Uri.parse(photoPath))
                        photoPath = null
                    }
                } else {
                    result.data?.dataString?.let {
                        results = arrayOf(Uri.parse(it))
                    }
                }
            }
            filePath?.onReceiveValue(results)
            filePath = null
        }


        @Throws(IOException::class)
        fun createPictureFromCamera(): File {
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                "JPEG_${System.currentTimeMillis()}", ".jpg", storageDir
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.webContainer.canGoBack()){
            Log.d("123123", "canGoBack")
            binding.webContainer.goBack()
        }else{
            Log.d("123123", "ELSE")
            //Do nothing
            finishAffinity()
        }

    }
}