// port-lint: tests tests/stream_pending.rs
package io.github.kotlinmania.tokiostream

import kotlin.test.Test
import kotlin.test.assertEquals

class StreamPendingTest {
    @Test
    fun basicUsage() {
        val stream = pending<Int>()
        assertEquals(0 to null, (stream as? Pending<*>)?.sizeHint())
    }
}
