// port-lint: source tokio-stream/src/stream_ext/peekable.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Stream returned by the [peekable] method.
 */
public class Peekable<T>(
    private val iterator: Iterator<T>,
) : Flow<T> {
    private var peeked: T? = null
    private var hasPeeked: Boolean = false

    public fun peek(): T? {
        if (!hasPeeked) {
            if (iterator.hasNext()) {
                peeked = iterator.next()
                hasPeeked = true
            } else {
                peeked = null
                hasPeeked = false
            }
        }
        return peeked
    }

    public fun next(): T? {
        if (hasPeeked) {
            val v = peeked
            peeked = null
            hasPeeked = false
            return v
        }
        return if (iterator.hasNext()) iterator.next() else null
    }

    override suspend fun collect(collector: FlowCollector<T>) {
        while (true) {
            val item = next() ?: break
            collector.emit(item)
        }
    }

    public companion object {
        public fun <T> new(stream: Sequence<T>): Peekable<T> = Peekable(stream.iterator())

        public fun <T> new(iterable: Iterable<T>): Peekable<T> = Peekable(iterable.iterator())
    }
}
