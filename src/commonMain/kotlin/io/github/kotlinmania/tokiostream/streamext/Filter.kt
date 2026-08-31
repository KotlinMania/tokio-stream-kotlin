// port-lint: source tokio-stream/src/stream_ext/filter.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Stream returned by the filter combinator.
 */
public class Filter<T>(
    private val stream: Flow<T>,
    private val predicate: (T) -> Boolean,
) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        stream.collect { value ->
            if (predicate(value)) {
                collector.emit(value)
            }
        }
    }

    public companion object {
        public fun <T> new(
            stream: Flow<T>,
            predicate: (T) -> Boolean,
        ): Filter<T> = Filter(stream, predicate)
    }
}
