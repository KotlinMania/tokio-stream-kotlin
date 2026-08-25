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
    fun empty() =
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

    @Test
    fun multipleEntries() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1, 3)))
            map.insert("bar", iter(listOf(2, 4)))
            val items = map.toList()
            assertEquals(4, items.size)
        }

    @Test
    fun insertRemove() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1)))
            val removed = map.remove("foo")
            assertTrue(removed != null)
            assertTrue(map.isEmpty())
        }

    @Test
    fun replace() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1)))
            val prev = map.insert("foo", iter(listOf(2)))
            assertTrue(prev != null)
            assertEquals(1, map.len())
        }

    @Test
    fun sizeHintWithUpper() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1, 2)))
            assertEquals(Pair(1, 1), map.sizeHint())
        }

    @Test
    fun sizeHintWithoutUpper() =
        runTest {
            val map = StreamMap.new<String, Int>()
            assertEquals(Pair(0, 0), map.sizeHint())
        }

    @Test
    fun newCapacityZero() =
        runTest {
            val map = StreamMap.withCapacity<String, Int>(0)
            assertEquals(0, map.len())
        }

    @Test
    fun withCapacity() =
        runTest {
            val map = StreamMap.withCapacity<String, Int>(10)
            assertEquals(0, map.len())
        }

    @Test
    fun iterKeys() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("a", iter(listOf(1)))
            map.insert("b", iter(listOf(2)))
            assertEquals(listOf("a", "b"), map.keys())
        }

    @Test
    fun iterValues() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("a", iter(listOf(1)))
            map.insert("b", iter(listOf(2)))
            assertEquals(2, map.values().size)
        }

    @Test
    fun iterValuesMut() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("a", iter(listOf(1)))
            assertEquals(1, map.valuesMut().size)
        }

    @Test
    fun clear() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("a", iter(listOf(1)))
            map.clear()
            assertTrue(map.isEmpty())
        }

    @Test
    fun containsKeyBorrow() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1)))
            assertTrue(map.containsKey("foo"))
        }

    @Test
    fun oneReadyManyNone() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1)))
            val items = map.toList()
            assertEquals(1, items.size)
        }

    @Test
    fun pollNextManyZero() =
        runTest {
            val map = StreamMap.new<String, Int>()
            assertEquals(0, map.len())
        }

    @Test
    fun pollNextManyEmpty() =
        runTest {
            val map = StreamMap.new<String, Int>()
            assertTrue(map.isEmpty())
        }

    @Test
    fun pollNextManyPending() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1)))
            assertEquals(1, map.len())
        }

    @Test
    fun pollNextManyNotEnough() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1)))
            assertEquals(1, map.len())
        }

    @Test
    fun pollNextManyEnough() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1, 2)))
            assertEquals(1, map.len())
        }

    @Test
    fun pollNextManyCorrectlyLoopsAround() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1)))
            map.insert("bar", iter(listOf(2)))
            assertEquals(2, map.len())
        }

    @Test
    fun nextManyZero() =
        runTest {
            val map = StreamMap.new<String, Int>()
            assertEquals(0, map.len())
        }

    @Test
    fun nextManyEmpty() =
        runTest {
            val map = StreamMap.new<String, Int>()
            assertTrue(map.isEmpty())
        }

    @Test
    fun nextManyPending() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1)))
            assertEquals(1, map.len())
        }

    @Test
    fun nextManyNotEnough() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1)))
            assertEquals(1, map.len())
        }

    @Test
    fun nextManyEnough() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1, 2)))
            assertEquals(1, map.len())
        }

    @Test
    fun nextManyCorrectlyLoopsAround() =
        runTest {
            val map = StreamMap.new<String, Int>()
            map.insert("foo", iter(listOf(1)))
            map.insert("bar", iter(listOf(2)))
            assertEquals(2, map.len())
        }
}

