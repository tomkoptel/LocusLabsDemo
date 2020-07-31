package com.olderwold.locuslabsdemo

import com.locuslabs.sdk.maps.model.VenueDatabase

internal interface LocusMaps {
    fun createDb(onReady: (VenueDatabase) -> Unit)

    fun close()
}
