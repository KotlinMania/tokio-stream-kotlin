// port-lint: tests tokio-stream/tests/stream_close.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StreamCloseTest {
    @Test
    fun basicUsage() =
        runTest {
            val stream = StreamNotifyClose.new(iter(listOf(0, 1)))
            val list = stream.toList()
            assertEquals(listOf(0, 1, null), list)
        }
}
