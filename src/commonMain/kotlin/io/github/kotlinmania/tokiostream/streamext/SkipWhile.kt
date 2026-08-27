// port-lint: source tokio-stream/src/stream_ext/skip_while.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Stream for the [skipWhile] method.
 */
public class SkipWhile<T>(
    private val stream: Flow<T>,
    private val predicate: (T) -> Boolean,
) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        var skipping = true
        stream.collect { value ->
            if (skipping) {
                if (!predicate(value)) {
                    skipping = false
                    collector.emit(value)
                }
            } else {
                collector.emit(value)
            }
        }
    }

    public companion object {
        public fun <T> new(
            stream: Flow<T>,
            predicate: (T) -> Boolean,
        ): SkipWhile<T> = SkipWhile(stream, predicate)
    }
}
