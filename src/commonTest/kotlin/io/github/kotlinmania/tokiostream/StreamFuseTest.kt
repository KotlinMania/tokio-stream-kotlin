// port-lint: tests tests/stream_fuse.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StreamFuseTest {
    @Test
    fun basicUsage() =
        runTest {
            val stream =
                flow {
                    emit(0)
                    emit(2)
                    emit(4)
                }

            val fused = stream.fuse()
            assertEquals(listOf(0, 2, 4), fused.toList())
        }
}
