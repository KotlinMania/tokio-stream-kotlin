// port-lint: tests tests/stream_chunks_timeout.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

class StreamChunksTimeoutTest {
    @Test
    fun testChunks() =
        runTest {
            val s = iter(listOf(1, 2, 3, 4, 5)).chunksTimeoutStream(2, 100.milliseconds)
            val chunks = s.toList()
            assertEquals(listOf(listOf(1, 2), listOf(3, 4), listOf(5)), chunks)
        }
}
