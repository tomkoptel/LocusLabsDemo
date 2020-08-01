package com.olderwold.locuslabsdemo.util

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dagger.hilt.EntryPoints
import javax.inject.Inject

@MainThread
inline fun <reified VM : ViewModel> Fragment.overridableViewModels(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this }
) = this.viewModels<VM>(ownerProducer, factoryProducer = {
    val entryPoints = EntryPoints.get(
        this,
        OverrideViewModelFactoryModules.FragmentEntryPoint::class.java
    )
    entryPoints.overrideFragmentViewModelFactory
})

class OverrideViewModelFactory @Inject constructor(
    private val delegate: ViewModelProvider.Factory,
    private val viewModelProducers: ViewModelProducers
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val instance = viewModelProducers.get(modelClass)
        return if (instance == null) {
            delegate.create(modelClass)
        } else {
            instance as T
        }
    }
}
