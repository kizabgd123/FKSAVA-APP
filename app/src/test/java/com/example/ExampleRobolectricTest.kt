package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("FK SAVA 45", appName)
  }

  @Test
  fun `verify pitch booking calculation and repository`() {
    val repo = com.example.data.repository.BookingRepository()
    val defaultDates = repo.availableDates
    org.junit.Assert.assertTrue(defaultDates.isNotEmpty())

    val smallPitch = com.example.data.model.PitchType.MALI
    val largePitch = com.example.data.model.PitchType.VELIKI
    org.junit.Assert.assertEquals(4000, smallPitch.baseRateRsd)
    org.junit.Assert.assertEquals(6000, largePitch.baseRateRsd)

    val duration90 = com.example.data.model.MatchDuration.MIN_90
    val totalSmall90 = (smallPitch.baseRateRsd * duration90.multiplier).toInt()
    org.junit.Assert.assertEquals(6000, totalSmall90)
  }
}
