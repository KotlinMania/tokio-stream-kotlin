// port-lint: source stream_ext/any.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow

/**
 * Helper for the [any] method.
 */
public object Any {
    public suspend fun <T> any(
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
