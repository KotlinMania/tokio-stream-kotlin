// port-lint: source stream_ext.rs
package io.github.kotlinmania.tokiostream

import io.github.kotlinmania.tokiostream.streamext.All
import io.github.kotlinmania.tokiostream.streamext.Any
import io.github.kotlinmania.tokiostream.streamext.Chain
import io.github.kotlinmania.tokiostream.streamext.ChunksTimeout
import io.github.kotlinmania.tokiostream.streamext.Collect
import io.github.kotlinmania.tokiostream.streamext.Filter
import io.github.kotlinmania.tokiostream.streamext.FilterMap
import io.github.kotlinmania.tokiostream.streamext.Fold
import io.github.kotlinmania.tokiostream.streamext.Fuse
import io.github.kotlinmania.tokiostream.streamext.Map
import io.github.kotlinmania.tokiostream.streamext.MapWhile
import io.github.kotlinmania.tokiostream.streamext.Merge
import io.github.kotlinmania.tokiostream.streamext.Next
import io.github.kotlinmania.tokiostream.streamext.Peekable
import io.github.kotlinmania.tokiostream.streamext.Skip
import io.github.kotlinmania.tokiostream.streamext.SkipWhile
import io.github.kotlinmania.tokiostream.streamext.Take
import io.github.kotlinmania.tokiostream.streamext.TakeWhile
import io.github.kotlinmania.tokiostream.streamext.Then
import io.github.kotlinmania.tokiostream.streamext.Throttle
import io.github.kotlinmania.tokiostream.streamext.Timeout
import io.github.kotlinmania.tokiostream.streamext.TimeoutRepeating
import io.github.kotlinmania.tokiostream.streamext.TryNext
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

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

    public fun <T> takeWhile(
        stream: Flow<T>,
        predicate: (T) -> Boolean,
    ): TakeWhile<T> = TakeWhile.new(stream, predicate)

    public fun <T> skipWhile(
        stream: Flow<T>,
        predicate: (T) -> Boolean,
    ): SkipWhile<T> = SkipWhile.new(stream, predicate)

    public fun <T, R> mapWhile(
        stream: Flow<T>,
        transform: (T) -> R?,
    ): MapWhile<T, R> = MapWhile.new(stream, transform)

    public fun <T, R> filterMap(
        stream: Flow<T>,
        transform: (T) -> R?,
    ): FilterMap<T, R> = FilterMap.new(stream, transform)

    public fun <T, R> then(
        stream: Flow<T>,
        transform: suspend (T) -> R,
    ): Then<T, R> = Then.new(stream, transform)

    public fun <T> merge(
        a: Flow<T>,
        b: Flow<T>,
    ): Merge<T> = Merge.new(a, b)

    public fun <T> peekable(sequence: Sequence<T>): Peekable<T> = Peekable.new(sequence)

    public fun <T> peekable(iterable: Iterable<T>): Peekable<T> = Peekable.new(iterable)

    public fun <T> timeout(
        stream: Flow<T>,
        duration: Duration,
    ): Timeout<T> = Timeout.new(stream, duration)

    public fun <T> timeoutRepeating(
        stream: Flow<T>,
        duration: Duration,
    ): TimeoutRepeating<T> = TimeoutRepeating.new(stream, duration)

    public fun <T> chunksTimeout(
        stream: Flow<T>,
        maxSize: Int,
        duration: Duration,
    ): ChunksTimeout<T> = ChunksTimeout.new(stream, maxSize, duration)

    public fun <T> throttle(
        stream: Flow<T>,
        duration: Duration,
    ): Throttle<T> = Throttle.new(stream, duration)

    public suspend fun <T> next(stream: Flow<T>): T? = Next(stream)

    public suspend fun <T> tryNext(stream: Flow<Result<T>>): Result<T?> = TryNext(stream)

    public suspend fun <T> all(
        stream: Flow<T>,
        predicate: (T) -> Boolean,
    ): Boolean = All(stream, predicate)

    public suspend fun <T> any(
        stream: Flow<T>,
        predicate: (T) -> Boolean,
    ): Boolean = Any(stream, predicate)

    public suspend fun <T, R> fold(
        stream: Flow<T>,
        initial: R,
        operation: (R, T) -> R,
    ): R = Fold(stream, initial, operation)

    public suspend fun <T> toList(stream: Flow<T>): List<T> = Collect.toList(stream)
}

public fun <T> Flow<T>.chain(other: Flow<T>): Chain<T> = StreamExt.chain(this, other)

public fun <T> Flow<T>.fuse(): Fuse<T> = StreamExt.fuse(this)

public fun <T> Flow<T>.filterStream(predicate: (T) -> Boolean): Filter<T> = StreamExt.filter(this, predicate)

public fun <T, R> Flow<T>.mapStream(transform: (T) -> R): Map<T, R> = StreamExt.map(this, transform)

public fun <T> Flow<T>.takeStream(n: Int): Take<T> = StreamExt.take(this, n)

public fun <T> Flow<T>.skipStream(n: Int): Skip<T> = StreamExt.skip(this, n)

public fun <T> Flow<T>.takeWhileStream(predicate: (T) -> Boolean): TakeWhile<T> = StreamExt.takeWhile(this, predicate)

public fun <T> Flow<T>.skipWhileStream(predicate: (T) -> Boolean): SkipWhile<T> = StreamExt.skipWhile(this, predicate)

public fun <T, R> Flow<T>.mapWhileStream(transform: (T) -> R?): MapWhile<T, R> =
    StreamExt.mapWhile(this, transform)

public fun <T, R> Flow<T>.filterMapStream(transform: (T) -> R?): FilterMap<T, R> =
    StreamExt.filterMap(this, transform)

public fun <T, R> Flow<T>.thenStream(transform: suspend (T) -> R): Then<T, R> = StreamExt.then(this, transform)

public fun <T> Flow<T>.mergeStream(other: Flow<T>): Merge<T> = StreamExt.merge(this, other)

public fun <T> Flow<T>.timeoutStream(duration: Duration): Timeout<T> = StreamExt.timeout(this, duration)

public fun <T> Flow<T>.timeoutRepeatingStream(duration: Duration): TimeoutRepeating<T> =
    StreamExt.timeoutRepeating(this, duration)

public fun <T> Flow<T>.chunksTimeoutStream(
    maxSize: Int,
    duration: Duration,
): ChunksTimeout<T> = StreamExt.chunksTimeout(this, maxSize, duration)

public fun <T> Flow<T>.throttleStream(duration: Duration): Throttle<T> = StreamExt.throttle(this, duration)

public suspend fun <T> Flow<T>.nextStream(): T? = StreamExt.next(this)

public suspend fun <T> Flow<Result<T>>.tryNextStream(): Result<T?> = StreamExt.tryNext(this)

public suspend fun <T> Flow<T>.allStream(predicate: (T) -> Boolean): Boolean = StreamExt.all(this, predicate)

public suspend fun <T> Flow<T>.anyStream(predicate: (T) -> Boolean): Boolean = StreamExt.any(this, predicate)

public suspend fun <T, R> Flow<T>.foldStream(
    initial: R,
    operation: (R, T) -> R,
): R = StreamExt.fold(this, initial, operation)

public suspend fun <T> Flow<T>.toListStream(): List<T> = StreamExt.toList(this)
