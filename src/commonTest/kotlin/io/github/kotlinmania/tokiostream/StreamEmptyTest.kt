// port-lint: tests stream_empty.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class StreamEmptyTest {
    @Test
    fun basicUsage() =
        runTest {
            val stream = empty<Int>()

            repeat(2) {
                assertEquals(0 to 0, (stream as? Empty<*>)?.sizeHint())
                assertNull(stream.firstOrNull())
            }
        }
}
