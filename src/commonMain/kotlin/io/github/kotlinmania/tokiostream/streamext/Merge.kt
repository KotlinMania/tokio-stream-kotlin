// port-lint: source stream_ext/merge.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.merge

/**
 * Stream returned by the [merge] method.
 */
public class Merge<T>(
    private val a: Flow<T>,
    private val b: Flow<T>,
) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        merge(a, b).collect(collector)
    }

    public companion object {
        public fun <T> new(
            a: Flow<T>,
            b: Flow<T>,
        ): Merge<T> = Merge(a, b)
    }
}
