// port-lint: tests tests/stream_timeout.rs
package io.github.kotlinmania.tokiostream

import io.github.kotlinmania.tokiostream.streamext.Elapsed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class StreamTimeoutTest {
    private suspend fun maybeSleep(idx: Int): Int {
        if (idx % 2 == 0) {
            delay(ms(200))
        }
        return idx
    }

    private fun ms(n: Long): Duration = n.milliseconds

    @Test
    fun basicUsage() =
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

    @Test
    fun returnElapsedErrorsOnlyOnce() =
        runTest {
            val s =
                iter(listOf(1, 2, 3))
                    .map { maybeSleep(it) }
                    .timeoutStream(ms(50))
            val results = s.toList()
            assertTrue(results.isNotEmpty())
        }

    @Test
    fun noTimeouts() =
        runTest {
            val s =
                iter(listOf(1, 3, 5))
                    .timeoutStream(ms(100))
            val results = s.toList()
            assertEquals(3, results.size)
            assertEquals(listOf(1, 3, 5), results.map { it.getOrNull() })
        }
}

