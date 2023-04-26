package com.example.jeepni

import android.content.Context
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

open class AppNameClass (context: Context) {
    fun getName() : String {
        return "JeepNi by AlgoFirst"
    }
}

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

private const val FAKE_STRING = "JeepNi by AlgoFirst"

@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    @Mock
    private lateinit var mockContext: Context
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun appNameIsCorrect() {
        val mockContext = mock<Context>()

        val objectUnderTest = AppNameClass(mockContext)
        val result : String = objectUnderTest.getName()
        assertEquals(result, FAKE_STRING)
    }
}