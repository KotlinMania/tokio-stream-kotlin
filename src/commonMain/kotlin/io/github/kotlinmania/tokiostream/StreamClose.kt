// port-lint: source stream_close.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * A [Flow] that wraps values in nullable elements, emitting `null` upon stream completion.
 */
public class StreamNotifyClose<T>(
    private var inner: Flow<T>?,
) : Flow<T?> {
    public fun intoInner(): Flow<T>? = inner

    override suspend fun collect(collector: FlowCollector<T?>) {
        val s = inner
        if (s == null) {
            collector.emit(null)
            return
        }
        try {
            s.collect { value ->
                collector.emit(value)
            }
            collector.emit(null)
        } finally {
            inner = null
        }
    }

    public companion object {
        public fun <T> new(stream: Flow<T>): StreamNotifyClose<T> = StreamNotifyClose(stream)
    }
}
