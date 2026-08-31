// port-lint: source tokio-stream/src/stream_ext/try_next.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

/**
 * Helper for the [tryNext] method.
 */
public object TryNext {
    public suspend operator fun <T> invoke(stream: Flow<Result<T>>): Result<T?> = execute(stream)

    public suspend fun <T> execute(stream: Flow<Result<T>>): Result<T?> {
        val item = stream.firstOrNull() ?: return Result.success(null)
        return item.map { it }
    }
}
