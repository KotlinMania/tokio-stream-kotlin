// port-lint: tests tests/stream_timeout.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds

class StreamTimeoutTest {
    @Test
    fun testBasicTimeout() =
        runTest {
            val s =
                flow {
                    emit(1)
                    delay(20.milliseconds)
                    emit(2)
                }.timeoutStream(100.milliseconds)

            val results = s.toList()
            assertEquals(2, results.size)
            assertTrue(results[0].isSuccess)
            assertEquals(1, results[0].getOrNull())
            assertTrue(results[1].isSuccess)
            assertEquals(2, results[1].getOrNull())
        }
}
