package com.production.gameplay

import android.app.Application
import com.onesignal.OneSignal

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setAppId("5a1598a6-a20d-415b-8b0e-8f4078d4f883")

    }

}