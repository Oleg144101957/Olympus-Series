package com.production.gameplay.utils

import android.content.Context
import android.provider.Settings
import android.util.Log

class HumanDetector() {

    companion object{
        fun isHuman(context: Context) : Boolean{
            val dba = Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED)
            val count = Settings.Global.getString(context.contentResolver, Settings.Global.BOOT_COUNT).toInt()
            Log.d("123123", "The adb is $dba the count is $count")

            return dba == "0"
        }
    }
}