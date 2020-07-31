package com.olderwold.locuslabsdemo

import android.app.Application
import com.locuslabs.sdk.configuration.LocusLabs
import javax.inject.Inject

internal class LocusMaps @Inject constructor(
    private val application: Application
) : AirportMapSDK {
    override fun init() {
        LocusLabs.initialize(application,"A11F4Y6SZRXH4X");
    }
}
