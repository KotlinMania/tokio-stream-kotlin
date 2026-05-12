// port-lint: source src/iter.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.yield

/** Stream for the [iter] function. */
class Iter<T> internal constructor(
    private val iter: Iterator<T>,
    private val knownSize: Pair<Int, Int?> = 0 to null,
) : Flow<T> {
    private var yieldAmt: Int = 0
    private var consumed: Int = 0

    override suspend fun collect(collector: FlowCollector<T>) {
        while (true) {
            if (yieldAmt >= 32) {
                yieldAmt = 0
                yield()
            } else {
                yieldAmt += 1
                if (!iter.hasNext()) return
                collector.emit(iter.next())
                consumed += 1
            }
        }
    }

    fun sizeHint(): Pair<Int, Int?> {
        val (lower, upper) = knownSize
        val remainingLower = (lower - consumed).coerceAtLeast(0)
        val remainingUpper = upper?.let { (it - consumed).coerceAtLeast(0) }
        return remainingLower to remainingUpper
    }
}

/**
 * Converts an [Iterable] into a [Flow] (the Kotlin analogue of Rust's `Stream`)
 * which is always ready to yield the next value.
 *
 * Iterators in Kotlin don't express the ability to suspend, so this adapter
 * simply always calls `iter.next()` and emits that.
 *
 * ```
 * val stream = iter(listOf(17, 19))
 *
 * check(stream.firstOrNull() == 17)
 * ```
 */
fun <T> iter(i: Iterable<T>): Iter<T> {
    val collection = i as? Collection<T>
    val size = collection?.size
    val hint: Pair<Int, Int?> = if (size != null) size to size else 0 to null
    return Iter(i.iterator(), hint)
}
