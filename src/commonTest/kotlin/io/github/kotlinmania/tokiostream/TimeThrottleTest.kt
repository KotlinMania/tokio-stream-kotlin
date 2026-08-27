// port-lint: tests tokio-stream/tests/time_throttle.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

class TimeThrottleTest {
    @Test
    fun usage() =
        runTest {
            val s = iter(listOf(1, 2, 3)).throttleStream(10.milliseconds)
            val results = s.toList()
            assertEquals(listOf(1, 2, 3), results)
        }
}
