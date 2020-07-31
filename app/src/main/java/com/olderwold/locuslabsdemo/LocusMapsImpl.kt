package com.olderwold.locuslabsdemo

import android.app.Application
import com.locuslabs.sdk.configuration.LocusLabs
import com.locuslabs.sdk.maps.model.VenueDatabase
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

internal class LocusMapsImpl @Inject constructor(
    private val application: Application
) : LocusMaps {
    private val initialized = AtomicBoolean()
    private val databases = Collections.synchronizedCollection(mutableListOf<VenueDatabase>())

    override fun createDb(onReady: (VenueDatabase) -> Unit) {
        val isInitialized = initialized.get()
        if (!isInitialized) {
            initialized.set(true)
            LocusLabs.initialize(application,"A11F4Y6SZRXH4X")
        }
        LocusLabs.registerOnReadyListener {
            val db = VenueDatabase()
            databases += db
            onReady(db)
        }
    }

    override fun close() {
        databases.forEach { it.close() }
        LocusLabs.close()
    }
}
