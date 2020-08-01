package com.olderwold.locuslabsdemo.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

object OverrideViewModelFactoryModules {
    @Module
    @InstallIn(FragmentComponent::class)
    object ViewModelProducersModule {
        @Provides
        fun getViewModelProducers(): ViewModelProducers {
            return ViewModelProducers()
        }
    }

    @Module
    @InstallIn(FragmentComponent::class)
    object FragmentModule {
        @Provides
        @ProxyFragmentViewModelFactory
        fun getProxyViewModelProviderFactory(
            fragment: Fragment,
            viewModelProducers: ViewModelProducers
        ): ViewModelProvider.Factory {
            return OverrideViewModelFactory(
                fragment.defaultViewModelProviderFactory,
                viewModelProducers
            )
        }
    }

    @EntryPoint
    @InstallIn(FragmentComponent::class)
    interface FragmentEntryPoint {
        @get:ProxyFragmentViewModelFactory
        val overrideFragmentViewModelFactory : ViewModelProvider.Factory
    }
}
