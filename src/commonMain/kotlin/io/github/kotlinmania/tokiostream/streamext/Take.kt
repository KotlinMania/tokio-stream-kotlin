// port-lint: source stream_ext/take.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Stream for the take combinator.
 */
public class Take<T>(
    private val stream: Flow<T>,
    private val limit: Int,
) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        if (limit <= 0) return
        var count = 0
        stream.collect { value ->
            if (count < limit) {
                collector.emit(value)
                count++
            }
        }
    }

    public companion object {
        public fun <T> new(
            stream: Flow<T>,
            limit: Int,
        ): Take<T> = Take(stream, limit)
    }
}
