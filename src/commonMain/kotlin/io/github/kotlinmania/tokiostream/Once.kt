// port-lint: source src/once.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/** Stream for the [once] function. */
class Once<T> internal constructor(private val iter: Iter<T>) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        iter.collect(collector)
    }

    fun sizeHint(): Pair<Int, Int?> = iter.sizeHint()
}

/**
 * Creates a stream that emits an element exactly once.
 *
 * The returned stream is immediately ready and emits the provided value once.
 *
 * ```
 * // one is the loneliest number
 * val one = once(1)
 *
 * check(one.firstOrNull() == 1)
 *
 * // just one, that's all we get
 * check(one.firstOrNull() == null)
 * ```
 */
fun <T> once(value: T): Once<T> = Once(iter(listOf(value)))
