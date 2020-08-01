package com.olderwold.locuslabsdemo.com.olderwold.locuslabsdemo

import android.util.Log
import androidx.test.core.app.launchActivity
import com.locuslabs.sdk.maps.model.VenueDatabase
import com.olderwold.locuslabsdemo.*
import com.olderwold.locuslabsdemo.util.OverrideViewModelFactoryModules
import com.olderwold.locuslabsdemo.util.viewModelProducers
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(OverrideViewModelFactoryModules.ViewModelProducersModule::class)
internal class MapFragmentTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @field:Inject
    lateinit var locusMaps: LocusMapsImpl

    private val viewModel = VenueViewModel(LocusMapsFake({ locusMaps }, "viewModel100"))
    private val viewModel2 = VenueViewModel2(LocusMapsFake({ locusMaps }, "viewModel200"))

    @BindValue
    @JvmField
    val overrides = viewModelProducers {
        register(viewModel)
        register(viewModel2)
    }

    @Test
    fun name() {
        hiltRule.inject()
        launchActivity<MainActivity>().use {
            Thread.sleep(5000)
        }
    }

    class LocusMapsFake(
        locusMapsProducer: () -> LocusMaps,
        private val message: String
    ) : LocusMaps {
        private val delegate by lazy(locusMapsProducer)

        override fun createDb(onReady: (VenueDatabase) -> Unit) {
            Log.d("LocusMapsFake", "I am from $message")
            delegate.createDb(onReady)
        }

        override fun close() {
            delegate.close()
        }
    }
}
