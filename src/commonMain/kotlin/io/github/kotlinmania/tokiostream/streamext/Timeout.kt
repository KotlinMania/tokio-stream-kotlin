// port-lint: source tokio-stream/src/stream_ext/timeout.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration

/**
 * Error returned by [Timeout].
 */
public class Elapsed : Exception("deadline has elapsed")

/**
 * Stream returned by the [timeout] method.
 */
public class Timeout<T>(
    private val stream: Flow<T>,
    private val duration: Duration,
) : Flow<Result<T>> {
    override suspend fun collect(collector: FlowCollector<Result<T>>) {
        try {
            stream.collect { value ->
                try {
                    withTimeout(duration) {
                        collector.emit(Result.success(value))
                    }
                } catch (e: TimeoutCancellationException) {
                    collector.emit(Result.failure(Elapsed()))
                }
            }
        } catch (e: TimeoutCancellationException) {
            collector.emit(Result.failure(Elapsed()))
        }
    }

    public companion object {
        public fun <T> new(
            stream: Flow<T>,
            duration: Duration,
        ): Timeout<T> = Timeout(stream, duration)
    }
}
