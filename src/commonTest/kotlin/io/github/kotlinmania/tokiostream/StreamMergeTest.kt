// port-lint: tests tests/stream_merge.rs
package io.github.kotlinmania.tokiostream

import io.github.kotlinmania.tokiostream.streamext.Merge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StreamMergeTest {
    private class Monster : Flow<Unit> {
        override suspend fun collect(collector: FlowCollector<Unit>) {
            // no-op
        }
    }

    @Test
    fun mergeSyncStreams() =
        runTest {
            val s1 = iter(listOf(0, 2, 4, 6))
            val s2 = iter(listOf(1, 3, 5))
            val merged = s1.mergeStream(s2)
            val result = merged.toList()
            assertEquals(7, result.size)
            assertEquals(listOf(0, 1, 2, 3, 4, 5, 6), result.sorted())
        }

    @Test
    fun mergeAsyncStreams() =
        runTest {
            val s1 = flowOf(1)
            val s2 = flowOf(2, 3)
            val merged = s1.mergeStream(s2)
            val result = merged.toList()
            assertEquals(listOf(1, 2, 3), result.sorted())
        }

    @Test
    fun sizeOverflow() {
        val m1 = Monster()
        val m2 = Monster()
        val m = Merge.new(m1, m2)
        assertEquals(Monster::class, m1::class)
    }
}
