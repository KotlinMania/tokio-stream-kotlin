// port-lint: source stream_ext/take_while.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Stream for the [takeWhile] method.
 */
public class TakeWhile<T>(
    private val stream: Flow<T>,
    private val predicate: (T) -> Boolean,
) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        try {
            stream.collect { value ->
                if (predicate(value)) {
                    collector.emit(value)
                } else {
                    throw TakeWhileAbortException()
                }
            }
        } catch (e: TakeWhileAbortException) {
            // normal termination
        }
    }

    private class TakeWhileAbortException : Exception()

    public companion object {
        public fun <T> new(
            stream: Flow<T>,
            predicate: (T) -> Boolean,
        ): TakeWhile<T> = TakeWhile(stream, predicate)
    }
}
