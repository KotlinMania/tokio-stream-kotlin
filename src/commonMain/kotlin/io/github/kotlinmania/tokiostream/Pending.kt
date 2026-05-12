// port-lint: source src/pending.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/** Stream for the [pending] function. */
class Pending<T> internal constructor() : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        awaitCancellation()
    }

    fun sizeHint(): Pair<Int, Int?> = 0 to null
}

/**
 * Creates a stream that is never ready.
 *
 * The returned stream is never ready. Attempting to call
 * [firstOrNull][kotlinx.coroutines.flow.firstOrNull] (the Kotlin analogue of Rust's
 * `StreamExt::next`) will never complete. Use [empty] to obtain a stream that
 * is immediately empty but returns no values.
 *
 * Basic usage:
 *
 * ```
 * val never = pending<Int>()
 *
 * // This will never complete
 * never.firstOrNull()
 *
 * error("unreachable")
 * ```
 */
fun <T> pending(): Pending<T> = Pending()
