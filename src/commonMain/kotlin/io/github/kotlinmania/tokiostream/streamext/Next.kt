// port-lint: source stream_ext/next.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

/**
 * Helper for the [next] method.
 */
public object Next {
    public suspend fun <T> next(stream: Flow<T>): T? = stream.firstOrNull()
}
