// port-lint: source tokio-stream/src/stream_ext/collect.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

/**
 * Helper for the [collect] method.
 */
public object Collect {
    public suspend fun <T> toList(stream: Flow<T>): List<T> = stream.toList()
}
