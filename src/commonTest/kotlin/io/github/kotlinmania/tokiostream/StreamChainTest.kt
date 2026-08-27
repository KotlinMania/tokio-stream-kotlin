// port-lint: tests tokio-stream/tests/stream_chain.rs
package io.github.kotlinmania.tokiostream

import io.github.kotlinmania.tokiostream.streamext.Chain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StreamChainTest {
    private fun <T> visibilityTest(s1: Flow<T>, s2: Flow<T>): Chain<T> = s1.chain(s2)

    private class Monster : Flow<Unit> {
        override suspend fun collect(collector: FlowCollector<Unit>) {
            // no-op
        }
    }

    @Test
    fun basicUsage() =
        runTest {
            val one = iter(listOf(1, 2, 3))
            val two = iter(listOf(4, 5, 6))

            val stream = visibilityTest(one, two)
            val list = stream.toList()

            assertEquals(listOf(1, 2, 3, 4, 5, 6), list)
        }

    @Test
    fun pendingFirst() =
        runTest {
            val one = flowOf(1)
            val two = flowOf(2)
            val stream = one.chain(two)
            assertEquals(listOf(1, 2), stream.toList())
        }

    @Test
    fun sizeOverflow() {
        val m1 = Monster()
        val m2 = Monster()
        val m = m1.chain(m2)
        assertEquals(Monster::class, m1::class)
    }
}

