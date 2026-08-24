// port-lint: source stream_ext/fold.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow

/**
 * Helper for the [fold] method.
 */
public object Fold {
    public suspend fun <T, R> fold(
        stream: Flow<T>,
        initial: R,
        operation: (R, T) -> R,
    ): R {
        var accumulator = initial
        stream.collect { value ->
            accumulator = operation(accumulator, value)
        }
        return accumulator
    }
}
