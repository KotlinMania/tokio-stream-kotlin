// port-lint: source tokio-stream/src/stream_ext/map.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Stream returned by the map combinator.
 */
public class Map<T, R>(
    private val stream: Flow<T>,
    private val transform: (T) -> R,
) : Flow<R> {
    override suspend fun collect(collector: FlowCollector<R>) {
        stream.collect { value ->
            collector.emit(transform(value))
        }
    }

    public companion object {
        public fun <T, R> new(
            stream: Flow<T>,
            transform: (T) -> R,
        ): Map<T, R> = Map(stream, transform)
    }
}
