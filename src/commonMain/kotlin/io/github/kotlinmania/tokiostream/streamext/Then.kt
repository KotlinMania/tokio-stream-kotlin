// port-lint: source tokio-stream/src/stream_ext/then.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Stream for the [then] method.
 */
public class Then<T, R>(
    private val stream: Flow<T>,
    private val transform: suspend (T) -> R,
) : Flow<R> {
    override suspend fun collect(collector: FlowCollector<R>) {
        stream.collect { value ->
            collector.emit(transform(value))
        }
    }

    public companion object {
        public fun <T, R> new(
            stream: Flow<T>,
            transform: suspend (T) -> R,
        ): Then<T, R> = Then(stream, transform)
    }
}
