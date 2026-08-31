// port-lint: source tokio-stream/src/stream_ext/any.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow

/**
 * Helper for the [any] method.
 */
public object Any {
    public suspend operator fun <T> invoke(
        stream: Flow<T>,
        predicate: (T) -> Boolean,
    ): Boolean = execute(stream, predicate)

    public suspend fun <T> execute(
        stream: Flow<T>,
        predicate: (T) -> Boolean,
    ): Boolean {
        var result = false
        try {
            stream.collect { value ->
                if (predicate(value)) {
                    result = true
                    throw AbortException()
                }
            }
        } catch (e: AbortException) {
            // early exit
        }
        return result
    }

    private class AbortException : Exception()
}
