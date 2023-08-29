package com.example.movieapp3.common

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

interface CoroutineScopeDispatchers {
    val IO: CoroutineDispatcher
    val Main: CoroutineDispatcher
}

class CoroutineScopeDispatchersImpl(
    override val IO: CoroutineDispatcher = Dispatchers.IO,
    override val Main: CoroutineDispatcher = Dispatchers.Main
) : CoroutineScopeDispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Singleton
    fun provideCoroutineScopeDispatchers(): CoroutineScopeDispatchers =
        CoroutineScopeDispatchersImpl()
}
