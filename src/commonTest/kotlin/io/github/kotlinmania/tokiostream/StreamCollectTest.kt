// port-lint: tests tests/stream_collect.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StreamCollectTest {
    @Test
    fun testEmptyVec() =
        runTest {
            val emptyStream = empty<Int>()
            val coll = emptyStream.toListStream()
            assertTrue(coll.isEmpty())
        }

    @Test
    fun testCollectItems() =
        runTest {
            val s = iter(listOf(1, 2, 3, 4))
            val coll = s.toListStream()
            assertEquals(listOf(1, 2, 3, 4), coll)
        }
}
