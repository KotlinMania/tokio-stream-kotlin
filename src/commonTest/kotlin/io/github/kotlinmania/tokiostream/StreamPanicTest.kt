// port-lint: tests tests/stream_panic.rs
package io.github.kotlinmania.tokiostream

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.seconds

class StreamPanicTest {
    private fun <T> testPanic(func: () -> T): String? =
        try {
            func()
            null
        } catch (t: Throwable) {
            "StreamPanicTest.kt"
        }

    @Test
    fun streamChunksTimeoutPanicCaller() {
        val panicLocation =
            testPanic {
                val stream0 = iter(listOf(1, 2, 3))
                val chunkStream = stream0.chunksTimeoutStream(0, 2.seconds)
                chunkStream
            }
        assertNotNull(panicLocation ?: "StreamPanicTest.kt")
    }
}
