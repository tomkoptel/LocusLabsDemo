package com.olderwold.locuslabsdemo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
internal abstract class AppModule {
    @Binds
    abstract fun airportMapSDK(maps: LocusMaps) : AirportMapSDK
}
