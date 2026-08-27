// port-lint: source tokio-stream/src/stream_ext/timeout_repeating.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlin.time.Duration

/**
 * Stream returned by the [timeoutRepeating] method.
 */
public class TimeoutRepeating<T>(
    private val stream: Flow<T>,
    private val duration: Duration,
) : Flow<Result<T>> {
    override suspend fun collect(collector: FlowCollector<Result<T>>) {
        Timeout(stream, duration).collect(collector)
    }

    public companion object {
        public fun <T> new(
            stream: Flow<T>,
            duration: Duration,
        ): TimeoutRepeating<T> = TimeoutRepeating(stream, duration)
    }
}
