// port-lint: tests tokio-stream/tests/stream_chunks_timeout.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

class StreamChunksTimeoutTest {
    @Test
    fun usage() =
        runTest {
            val s = iter(listOf(1, 2, 3, 4)).chunksTimeoutStream(4, 100.milliseconds)
            val chunks = s.toList()
            assertEquals(listOf(listOf(1, 2, 3, 4)), chunks)
        }

    @Test
    fun fullChunkWithTimeout() =
        runTest {
            val s = iter(listOf(1, 2, 3, 4)).chunksTimeoutStream(3, 100.milliseconds)
            val chunks = s.toList()
            assertEquals(listOf(listOf(1, 2, 3), listOf(4)), chunks)
        }

    @Test
    fun realTime() =
        runTest {
            val s = iter(listOf(1, 2, 3, 4, 5)).chunksTimeoutStream(3, 100.milliseconds)
            val chunks = s.toList()
            assertEquals(listOf(listOf(1, 2, 3), listOf(4, 5)), chunks)
        }

    @Test
    fun streamChunksRemainder() =
        runTest {
            val s = iter(listOf(1, 2, 3, 4)).chunksTimeoutStream(10, 100.milliseconds)
            val chunks = s.toList()
            assertEquals(listOf(listOf(1, 2, 3, 4)), chunks)
        }
}
