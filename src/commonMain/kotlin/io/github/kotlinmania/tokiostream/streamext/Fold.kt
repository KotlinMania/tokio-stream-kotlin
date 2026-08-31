// port-lint: source tokio-stream/src/stream_ext/fold.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow

/**
 * Helper for the [fold] method.
 */
public object Fold {
    public suspend operator fun <T, R> invoke(
        stream: Flow<T>,
        initial: R,
        operation: (R, T) -> R,
    ): R = execute(stream, initial, operation)

    public suspend fun <T, R> execute(
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
