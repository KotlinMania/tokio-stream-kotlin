// port-lint: source stream_ext/map_while.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Stream for the [mapWhile] method.
 */
public class MapWhile<T, R>(
    private val stream: Flow<T>,
    private val transform: (T) -> R?,
) : Flow<R> {
    override suspend fun collect(collector: FlowCollector<R>) {
        try {
            stream.collect { value ->
                val mapped = transform(value)
                if (mapped != null) {
                    collector.emit(mapped)
                } else {
                    throw MapWhileAbortException()
                }
            }
        } catch (e: MapWhileAbortException) {
            // normal termination
        }
    }

    private class MapWhileAbortException : Exception()

    public companion object {
        public fun <T, R> new(
            stream: Flow<T>,
            transform: (T) -> R?,
        ): MapWhile<T, R> = MapWhile(stream, transform)
    }
}
