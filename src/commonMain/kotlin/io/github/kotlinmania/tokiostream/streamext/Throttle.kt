// port-lint: source stream_ext/throttle.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlin.time.Duration

/**
 * Stream for the [throttle] method.
 */
public class Throttle<T>(
    private val stream: Flow<T>,
    private val duration: Duration,
) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        stream.collect { value ->
            collector.emit(value)
            delay(duration)
        }
    }

    public companion object {
        public fun <T> new(
            stream: Flow<T>,
            duration: Duration,
        ): Throttle<T> = Throttle(stream, duration)
    }
}
