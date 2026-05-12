// port-lint: source src/empty.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/** Stream for the [empty] function. */
class Empty<T> internal constructor() : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        // Streams do nothing unless polled; this one is immediately complete.
    }

    fun sizeHint(): Pair<Int, Int?> = 0 to 0
}

/**
 * Creates a stream that yields nothing.
 *
 * The returned stream is immediately ready and returns `null`. Use
 * [pending] to obtain a stream that is never ready.
 *
 * Basic usage:
 *
 * ```
 * val none = empty<Int>()
 * check(none.firstOrNull() == null)
 * ```
 */
fun <T> empty(): Empty<T> = Empty()
