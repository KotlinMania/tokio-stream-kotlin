// port-lint: source stream_ext/chain.rs
package io.github.kotlinmania.tokiostream.streamext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Stream returned by the chain combinator.
 */
public class Chain<T>(
    private val a: Flow<T>,
    private val b: Flow<T>,
) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        a.collect(collector)
        b.collect(collector)
    }

    public companion object {
        public fun <T> new(
            a: Flow<T>,
            b: Flow<T>,
        ): Chain<T> = Chain(a, b)
    }
}
