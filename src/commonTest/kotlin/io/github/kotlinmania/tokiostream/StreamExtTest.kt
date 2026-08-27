// port-lint: tests tokio-stream/src/stream_ext.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StreamExtTest {
    @Test
    fun testCombinators() =
        runTest {
            val s = iter(listOf(1, 2, 3, 4, 5, 6))

            val filtered = s.filterStream { it % 2 == 0 }
            assertEquals(listOf(2, 4, 6), filtered.toList())

            val mapped = iter(listOf(1, 2, 3)).mapStream { it * 10 }
            assertEquals(listOf(10, 20, 30), mapped.toList())

            val taken = iter(listOf(1, 2, 3, 4, 5)).takeStream(3)
            assertEquals(listOf(1, 2, 3), taken.toList())

            val skipped = iter(listOf(1, 2, 3, 4, 5)).skipStream(3)
            assertEquals(listOf(4, 5), skipped.toList())

            val nextVal = StreamExt.next(iter(listOf(42, 99)))
            assertEquals(42, nextVal)
        }

    @Test
    fun testTakeWhileAndSkipWhile() =
        runTest {
            val s1 = iter(listOf(1, 2, 3, 4, 1, 2)).takeWhileStream { it < 3 }
            assertEquals(listOf(1, 2), s1.toList())

            val s2 = iter(listOf(1, 2, 3, 4, 5)).skipWhileStream { it < 3 }
            assertEquals(listOf(3, 4, 5), s2.toList())
        }

    @Test
    fun testMapWhileAndFilterMap() =
        runTest {
            val s1 = iter(listOf(1, 2, 3, 4, 5)).mapWhileStream { if (it < 4) it * 2 else null }
            assertEquals(listOf(2, 4, 6), s1.toList())

            val s2 = iter(listOf(1, 2, 3, 4, 5)).filterMapStream { if (it % 2 != 0) it * 10 else null }
            assertEquals(listOf(10, 30, 50), s2.toList())
        }

    @Test
    fun testThenAndFold() =
        runTest {
            val s1 = iter(listOf(1, 2, 3)).thenStream { it * 5 }
            assertEquals(listOf(5, 10, 15), s1.toList())

            val total = iter(listOf(1, 2, 3, 4)).foldStream(0) { acc, elem -> acc + elem }
            assertEquals(10, total)
        }

    @Test
    fun testAllAndAny() =
        runTest {
            val s1 = iter(listOf(2, 4, 6, 8))
            assertTrue(s1.allStream { it % 2 == 0 })

            val s2 = iter(listOf(1, 3, 5, 6))
            assertTrue(s2.anyStream { it % 2 == 0 })

            val s3 = iter(listOf(1, 3, 5))
            assertFalse(s3.anyStream { it % 2 == 0 })
        }

    @Test
    fun testPeekable() =
        runTest {
            val peekable = StreamExt.peekable(listOf(10, 20, 30))
            assertEquals(10, peekable.peek())
            assertEquals(10, peekable.peek())
            assertEquals(10, peekable.next())
            assertEquals(20, peekable.next())
            assertEquals(30, peekable.peek())
            assertEquals(30, peekable.next())
            assertEquals(null, peekable.next())
        }

    @Test
    fun testTryNext() =
        runTest {
            val s = flowOf(Result.success(10), Result.success(20))
            val first = s.tryNextStream()
            assertTrue(first.isSuccess)
            assertEquals(10, first.getOrNull())
        }
}
