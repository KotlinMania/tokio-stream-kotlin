// port-lint: source tokio-stream/src/stream_map.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Combine many streams into one, indexing each source stream with a unique key.
 */
public class StreamMap<K, V> : Flow<Pair<K, V>> {
    private val entries: MutableList<Pair<K, Flow<V>>> = mutableListOf()

    public fun insert(
        key: K,
        stream: Flow<V>,
    ): Flow<V>? {
        val idx = entries.indexOfFirst { it.first == key }
        val old = if (idx >= 0) entries.removeAt(idx).second else null
        entries.add(key to stream)
        return old
    }

    public fun remove(key: K): Flow<V>? {
        val idx = entries.indexOfFirst { it.first == key }
        return if (idx >= 0) entries.removeAt(idx).second else null
    }

    public fun containsKey(key: K): Boolean = entries.any { it.first == key }

    public fun withCapacity(capacity: Int): StreamMap<K, V> = StreamMap()

    public fun capacity(): Int = entries.size

    public fun keys(): List<K> = entries.map { it.first }

    public fun values(): List<Flow<V>> = entries.map { it.second }

    public fun valuesMut(): List<Flow<V>> = entries.map { it.second }

    public fun clear() {
        entries.clear()
    }

    public fun sizeHint(): Pair<Int, Int?> = Pair(entries.size, entries.size)

    public fun len(): Int = entries.size

    public fun isEmpty(): Boolean = entries.isEmpty()

    override suspend fun collect(collector: FlowCollector<Pair<K, V>>) {
        for ((key, stream) in entries.toList()) {
            stream.collect { value ->
                collector.emit(key to value)
            }
        }
    }

    public companion object {
        public fun <K, V> new(): StreamMap<K, V> = StreamMap()

        public fun <K, V> withCapacity(capacity: Int): StreamMap<K, V> = StreamMap()
    }
}
