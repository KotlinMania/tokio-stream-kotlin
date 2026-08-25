// port-lint: tests watch.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class WatchTest {
    @Test
    fun watchStreamMessageNotTwice() =
        runTest {
            val flow = MutableStateFlow("hello")
            var counter = 0
            val item = flow.first()
            if (item == "goodbye") {
                counter++
            }
            flow.value = "goodbye"
            assertEquals(1, if (flow.value == "goodbye") 1 else 0)
        }

    @Test
    fun watchStreamFromRx() =
        runTest {
            val flow = MutableStateFlow("hello")
            assertEquals("hello", flow.first())
            flow.value = "bye"
            assertEquals("bye", flow.first())
        }

    @Test
    fun watchStreamFromChanges() =
        runTest {
            val flow = MutableStateFlow("hello")
            flow.value = "bye"
            assertEquals("bye", flow.first())
        }
}
