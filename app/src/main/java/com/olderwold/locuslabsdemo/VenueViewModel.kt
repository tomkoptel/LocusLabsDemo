package com.olderwold.locuslabsdemo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.locuslabs.sdk.configuration.LocusLabs
import com.locuslabs.sdk.maps.model.VenueDatabase

internal class VenueViewModel @ViewModelInject constructor() : ViewModel() {
    private val mutableState = MutableLiveData<VenueDatabase>()

    val state: LiveData<VenueDatabase> = mutableState

    fun load() {
        LocusLabs.registerOnReadyListener {
            mutableState.value = VenueDatabase()
        }
    }

    override fun onCleared() {
        state.value?.close()
        LocusLabs.close()
    }
}
