// port-lint: tests tokio-stream/src/macros.rs
package io.github.kotlinmania.tokiostream

import kotlin.test.Test
import kotlin.test.assertEquals

class MacrosTest {
    @Test
    fun testStreamFeaturesConstants() {
        assertEquals("time", StreamFeatures.FEATURE_TIME)
        assertEquals("net", StreamFeatures.FEATURE_NET)
        assertEquals("fs", StreamFeatures.FEATURE_FS)
        assertEquals("io-util", StreamFeatures.FEATURE_IO_UTIL)
        assertEquals("sync", StreamFeatures.FEATURE_SYNC)
        assertEquals("signal", StreamFeatures.FEATURE_SIGNAL)
    }
}
