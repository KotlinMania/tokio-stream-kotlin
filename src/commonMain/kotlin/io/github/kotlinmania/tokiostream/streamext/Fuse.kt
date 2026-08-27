// port-lint: source stream_ext/fuse.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Stream returned by [StreamExt.fuse].
 */
public class Fuse<T>(
    private var stream: Flow<T>?,
) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        val s = stream ?: return
        try {
            s.collect { value ->
                collector.emit(value)
            }
        } finally {
            stream = null
        }
    }

    public companion object {
        public fun <T> new(stream: Flow<T>): Fuse<T> = Fuse(stream)
    }
}
