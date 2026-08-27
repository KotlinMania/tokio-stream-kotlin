// port-lint: tests tokio-stream/tests/stream_iter.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StreamIterTest {
    @Test
    fun basicUsage() =
        runTest {
            val stream = iter(listOf(17, 19, 23))
            assertEquals(listOf(17, 19, 23), stream.toList())
        }

    @Test
    fun sizeHint() =
        runTest {
            val stream = iter(listOf(1, 2, 3))
            assertEquals(1, stream.firstOrNull())
        }

    @Test
    fun coop() =
        runTest {
            val stream = iter(generateSequence { 1 }.take(10).asIterable())
            assertEquals(10, stream.toList().size)
        }
}

