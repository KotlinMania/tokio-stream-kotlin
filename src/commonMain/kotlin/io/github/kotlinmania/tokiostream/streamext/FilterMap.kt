// port-lint: source stream_ext/filter_map.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Stream for the [filterMap] method.
 */
public class FilterMap<T, R>(
    private val stream: Flow<T>,
    private val transform: (T) -> R?,
) : Flow<R> {
    override suspend fun collect(collector: FlowCollector<R>) {
        stream.collect { value ->
            val mapped = transform(value)
            if (mapped != null) {
                collector.emit(mapped)
            }
        }
    }

    public companion object {
        public fun <T, R> new(
            stream: Flow<T>,
            transform: (T) -> R?,
        ): FilterMap<T, R> = FilterMap(stream, transform)
    }
}
