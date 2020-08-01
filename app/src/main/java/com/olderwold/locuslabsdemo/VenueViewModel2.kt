package com.olderwold.locuslabsdemo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.locuslabs.sdk.maps.model.VenueDatabase

internal class VenueViewModel2 @ViewModelInject constructor(
    private val locusMaps: LocusMaps
) : ViewModel() {
    private val mutableState = MutableLiveData<VenueDatabase>()
    val state: LiveData<VenueDatabase> = mutableState

    fun load() {
        locusMaps.createDb {
            mutableState.value = it
        }
    }

    override fun onCleared() {
        locusMaps.close()
    }
}
