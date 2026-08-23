// port-lint: tests stream_chain.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StreamChainTest {
    @Test
    fun basicUsage() =
        runTest {
            val one = iter(listOf(1, 2, 3))
            val two = iter(listOf(4, 5, 6))

            val stream = one.chain(two)
            val list = stream.toList()

            assertEquals(listOf(1, 2, 3, 4, 5, 6), list)
        }
}
