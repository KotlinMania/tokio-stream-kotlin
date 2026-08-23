// port-lint: source stream_ext.rs
package io.github.kotlinmania.tokiostream

import io.github.kotlinmania.tokiostream.streamext.Chain
import io.github.kotlinmania.tokiostream.streamext.Filter
import io.github.kotlinmania.tokiostream.streamext.Fuse
import io.github.kotlinmania.tokiostream.streamext.Map
import io.github.kotlinmania.tokiostream.streamext.Skip
import io.github.kotlinmania.tokiostream.streamext.Take
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

/**
 * Extension functions for [Flow] matching tokio_stream::StreamExt.
 */
public object StreamExt {
    public fun <T> chain(
        a: Flow<T>,
        b: Flow<T>,
    ): Chain<T> = Chain.new(a, b)

    public fun <T> fuse(stream: Flow<T>): Fuse<T> = Fuse.new(stream)

    public fun <T> filter(
        stream: Flow<T>,
        predicate: (T) -> Boolean,
    ): Filter<T> = Filter.new(stream, predicate)

    public fun <T, R> map(
        stream: Flow<T>,
        transform: (T) -> R,
    ): Map<T, R> = Map.new(stream, transform)

    public fun <T> take(
        stream: Flow<T>,
        n: Int,
    ): Take<T> = Take.new(stream, n)

    public fun <T> skip(
        stream: Flow<T>,
        n: Int,
    ): Skip<T> = Skip.new(stream, n)

    public suspend fun <T> next(stream: Flow<T>): T? = stream.firstOrNull()
}

public fun <T> Flow<T>.chain(other: Flow<T>): Chain<T> = StreamExt.chain(this, other)

public fun <T> Flow<T>.fuse(): Fuse<T> = StreamExt.fuse(this)

public fun <T> Flow<T>.filterStream(predicate: (T) -> Boolean): Filter<T> = StreamExt.filter(this, predicate)

public fun <T, R> Flow<T>.mapStream(transform: (T) -> R): Map<T, R> = StreamExt.map(this, transform)

public fun <T> Flow<T>.takeStream(n: Int): Take<T> = StreamExt.take(this, n)

public fun <T> Flow<T>.skipStream(n: Int): Skip<T> = StreamExt.skip(this, n)
