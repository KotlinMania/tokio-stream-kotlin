// port-lint: tests stream_ext.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

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
}
