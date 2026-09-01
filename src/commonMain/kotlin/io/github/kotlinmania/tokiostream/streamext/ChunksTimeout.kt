// port-lint: source stream_ext/chunks_timeout.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlin.time.Duration

/**
 * Stream returned by the [chunksTimeout] method.
 */
public class ChunksTimeout<T>(
    private val stream: Flow<T>,
    private val maxSize: Int,
    private val duration: Duration,
) : Flow<List<T>> {
    override suspend fun collect(collector: FlowCollector<List<T>>) {
        if (maxSize <= 0) return
        val buffer = mutableListOf<T>()
        stream.collect { item ->
            buffer.add(item)
            if (buffer.size >= maxSize) {
                collector.emit(buffer.toList())
                buffer.clear()
            }
        }
        if (buffer.isNotEmpty()) {
            collector.emit(buffer.toList())
            buffer.clear()
        }
    }

    public companion object {
        public fun <T> new(
            stream: Flow<T>,
            maxSize: Int,
            duration: Duration,
        ): ChunksTimeout<T> = ChunksTimeout(stream, maxSize, duration)
    }
}
