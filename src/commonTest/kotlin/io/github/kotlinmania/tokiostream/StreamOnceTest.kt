// port-lint: tests tests/stream_once.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StreamOnceTest {
    @Test
    fun basicUsage() =
        runTest {
            val one = once(1)

            assertEquals(1 to 1, (one as? Once<*>)?.sizeHint())
            assertEquals(1, one.firstOrNull())
        }
}
