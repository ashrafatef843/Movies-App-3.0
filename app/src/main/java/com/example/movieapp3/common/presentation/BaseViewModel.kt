package com.example.movieapp3.common.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp3.common.CoroutineScopeDispatchers
import com.example.movieapp3.common.presentation.dto.Async
import com.example.movieapp3.common.presentation.dto.Fail
import com.example.movieapp3.common.presentation.dto.Loading
import com.example.movieapp3.common.presentation.dto.Success
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel(
    private val coroutineScopeDispatchers: CoroutineScopeDispatchers
) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    /**
     * Extension function on any suspend function that will be executed in view model scope
     * and :
     * - call the reducer function with [AsyncStatus.Loading] before execute suspend function.
     * - call the reducer function with [AsyncStatus.Success] after execute suspend function.
     * - call the reducer function with [AsyncStatus.Fail] after throwing exception from executing.
     * suspend function.
     *
     * @param dispatcher: which will the view model scope will work on.
     * @param retainValue: which will be added to Loading or Fail as a latest value presented in
     * the view.
     * @param reducer: function that will be called each time with previous mentioned values
     */
    fun <T : Any?> (suspend () -> T).execute(
        dispatcher: CoroutineDispatcher? = null,
        retainValue: Async<T>? = null,
        reducer: (Async<T>) -> Unit
    ): Job {
        reducer(Loading(value = retainValue?.invoke()))

        return viewModelScope.launch(dispatcher ?: coroutineScopeDispatchers.Main) {
            try {
                val result = invoke()
                reducer(
                    Success(result)
                )
            } catch (e: CancellationException) {
                @Suppress("RethrowCaughtException")
                throw e
            } catch (@Suppress("TooGenericExceptionCaught") e: Throwable) {
                reducer(
                    Fail(
                        e,
                        value = retainValue?.invoke()
                    )
                )
            }
        }
    }
}
