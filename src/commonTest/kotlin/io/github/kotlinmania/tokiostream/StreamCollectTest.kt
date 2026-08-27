// port-lint: tests tokio-stream/tests/stream_collect.rs
package io.github.kotlinmania.tokiostream

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StreamCollectTest {
    @Test
    fun emptyUnit() =
        runTest {
            val emptyStream = empty<Unit>()
            val coll = emptyStream.toListStream()
            assertTrue(coll.isEmpty())
        }

    @Test
    fun emptyVec() =
        runTest {
            val emptyStream = empty<Int>()
            val coll = emptyStream.toListStream()
            assertTrue(coll.isEmpty())
        }

    @Test
    fun emptyBoxSlice() =
        runTest {
            val emptyStream = empty<Int>()
            val coll = emptyStream.toListStream()
            assertTrue(coll.isEmpty())
        }

    @Test
    fun emptyString() =
        runTest {
            val emptyStream = empty<String>()
            val coll = emptyStream.toListStream()
            assertTrue(coll.isEmpty())
        }

    @Test
    fun emptyResult() =
        runTest {
            val emptyStream = empty<Result<Int>>()
            val coll = emptyStream.toListStream()
            assertTrue(coll.isEmpty())
        }

    @Test
    fun collectVecItems() =
        runTest {
            val s = iter(listOf(1, 2, 3, 4))
            val coll = s.toListStream()
            assertEquals(listOf(1, 2, 3, 4), coll)
        }

    @Test
    fun collectStringItems() =
        runTest {
            val s = iter(listOf("hello ", "world"))
            val coll = s.toListStream()
            assertEquals("hello world", coll.joinToString(""))
        }

    @Test
    fun collectStrItems() =
        runTest {
            val s = iter(listOf("hello ", "world"))
            val coll = s.toListStream()
            assertEquals("hello world", coll.joinToString(""))
        }

    @Test
    fun collectResultsOk() =
        runTest {
            val s = iter(listOf(Result.success("hello "), Result.success("world")))
            val coll = s.toListStream()
            assertEquals(2, coll.size)
            assertEquals("hello world", coll.joinToString("") { it.getOrThrow() })
        }

    @Test
    fun collectResultsErr() =
        runTest {
            val s = iter(listOf(Result.failure<String>(IllegalStateException("oh no"))))
            val coll = s.toListStream()
            assertEquals(1, coll.size)
            assertTrue(coll[0].isFailure)
        }
}

