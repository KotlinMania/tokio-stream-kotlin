// port-lint: tests tests/stream_merge.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StreamMergeTest {
    @Test
    fun testMergeSyncStreams() =
        runTest {
            val s1 = iter(listOf(0, 2, 4, 6))
            val s2 = iter(listOf(1, 3, 5))
            val merged = s1.mergeStream(s2)
            val result = merged.toList()
            assertEquals(7, result.size)
            assertEquals(listOf(0, 1, 2, 3, 4, 5, 6), result.sorted())
        }
}
