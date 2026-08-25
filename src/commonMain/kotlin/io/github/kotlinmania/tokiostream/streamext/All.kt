// port-lint: source stream_ext/all.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow

/**
 * Helper for the [all] method.
 */
public object All {
    public suspend operator fun <T> invoke(
        stream: Flow<T>,
        predicate: (T) -> Boolean,
    ): Boolean = execute(stream, predicate)

    public suspend fun <T> execute(
        stream: Flow<T>,
        predicate: (T) -> Boolean,
    ): Boolean {
        var result = true
        try {
            stream.collect { value ->
                if (!predicate(value)) {
                    result = false
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
