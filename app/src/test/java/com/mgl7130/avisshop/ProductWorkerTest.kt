package com.mgl7130.avisshop

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.OneTimeWorkRequestBuilder
import com.mgl7130.avisshop.util.ProductWorker
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MyWorkTest {
    private lateinit var context: Context
    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }
    @Test
    fun testProductWork() {
        // Get the ListenableWorker
        val worker =
            OneTimeWorkRequestBuilder<ProductWorker>().build()
        // Run the worker synchronously
    }
}