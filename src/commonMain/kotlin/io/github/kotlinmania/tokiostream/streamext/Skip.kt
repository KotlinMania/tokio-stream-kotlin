// port-lint: source stream_ext/skip.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Stream for the skip combinator.
 */
public class Skip<T>(
    private val stream: Flow<T>,
    private val count: Int,
) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        var skipped = 0
        stream.collect { value ->
            if (skipped < count) {
                skipped++
            } else {
                collector.emit(value)
            }
        }
    }

    public companion object {
        public fun <T> new(
            stream: Flow<T>,
            count: Int,
        ): Skip<T> = Skip(stream, count)
    }
}
