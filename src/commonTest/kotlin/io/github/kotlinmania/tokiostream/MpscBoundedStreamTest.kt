// port-lint: tests tokio-stream/tests/mpsc_bounded_stream.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MpscBoundedStreamTest {
    @Test
    fun sizeHintStreamOpen() =
        runTest {
            val tx = Channel<Int>(4)
            tx.send(1)
            tx.send(2)
            tx.close()
            val list = tx.consumeAsFlow().toList()
            assertEquals(listOf(1, 2), list)
        }

    @Test
    fun sizeHintStreamClosed() =
        runTest {
            val tx = Channel<Int>(4)
            tx.send(1)
            tx.send(2)
            tx.close()
            val list = tx.consumeAsFlow().toList()
            assertEquals(listOf(1, 2), list)
        }

    @Test
    fun sizeHintSenderDropped() =
        runTest {
            val tx = Channel<Int>(4)
            tx.send(1)
            tx.send(2)
            tx.close()
            val list = tx.consumeAsFlow().toList()
            assertEquals(listOf(1, 2), list)
        }

    @Test
    fun sizeHintStreamInstantlyClosed() {
        val tx = Channel<Int>(4)
        tx.close()
        assertEquals(true, tx.trySend(1).isFailure)
    }

    @Test
    fun sizeHintStreamClosedPermitsSend() =
        runTest {
            val tx = Channel<Int>(4)
            tx.send(1)
            tx.send(2)
            tx.close()
            val list = tx.consumeAsFlow().toList()
            assertEquals(listOf(1, 2), list)
        }

    @Test
    fun sizeHintStreamClosedPermitsDrop() =
        runTest {
            val tx = Channel<Int>(4)
            tx.send(1)
            tx.close()
            val list = tx.consumeAsFlow().toList()
            assertEquals(listOf(1), list)
        }
}
