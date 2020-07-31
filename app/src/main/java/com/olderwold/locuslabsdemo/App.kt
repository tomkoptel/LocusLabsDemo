package com.olderwold.locuslabsdemo

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
internal class App : Application() {
    @Inject
    lateinit var mapSDK: AirportMapSDK

    override fun onCreate() {
        super.onCreate()
        mapSDK.init()
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }
}
