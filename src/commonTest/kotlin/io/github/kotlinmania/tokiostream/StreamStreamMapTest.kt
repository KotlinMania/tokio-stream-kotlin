// port-lint: tests stream_stream_map.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class StreamStreamMapTest {
    @Test
    fun emptyMap() =
        runTest {
            val map = StreamMap.new<String, Unit>()
            assertEquals(0, map.len())
            assertTrue(map.isEmpty())
            assertNull(map.remove("foo"))
        }

    @Test
    fun singleEntry() =
        runTest {
            val map = StreamMap.new<String, Int>()
            assertNull(map.insert("foo", iter(listOf(1, 2))))
            assertTrue(map.containsKey("foo"))
            assertFalse(map.containsKey("bar"))
            assertEquals(1, map.len())
            assertFalse(map.isEmpty())

            val items = map.toList()
            assertEquals(listOf("foo" to 1, "foo" to 2), items)
        }
}
