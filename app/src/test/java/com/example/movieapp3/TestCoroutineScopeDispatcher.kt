package com.example.movieapp3

import com.example.movieapp3.common.CoroutineScopeDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestCoroutineScopeDispatcher(
    val testCoroutineScheduler: TestCoroutineScheduler = TestCoroutineScheduler(),
    override val IO: CoroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler),
    override val Main: CoroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)
) : CoroutineScopeDispatchers {

}