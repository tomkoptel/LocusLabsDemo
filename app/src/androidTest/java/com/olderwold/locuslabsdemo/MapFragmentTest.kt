package com.olderwold.locuslabsdemo.com.olderwold.locuslabsdemo

import androidx.test.ext.junit.rules.activityScenarioRule
import com.olderwold.locuslabsdemo.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MapFragmentTest {
    @get:Rule(order = 0)
    val rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    internal val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun name() {
        Thread.sleep(5000)
    }
}
