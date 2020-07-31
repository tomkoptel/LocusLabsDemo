package com.olderwold.locuslabsdemo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
internal abstract class AppModule {
    @Binds
    @Singleton
    abstract fun airportMapSDK(mapsImpl: LocusMapsImpl) : LocusMaps
}
