package dev.hawk0f.itutor.core.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hawk0f.itutor.core.domain.Either
import dev.hawk0f.itutor.core.domain.NetworkError
import dev.hawk0f.itutor.core.presentation.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Base class for all [ViewModel]s
 */
abstract class BaseViewModel : ViewModel()
{
    /**
     * Collect network request result without mapping for primitive types
     *
     * @receiver [collectEither]
     */
    protected fun <T> Flow<Either<NetworkError, T>>.collectNetworkRequest(state: MutableStateFlow<UIState<T>>) = collectEither(state) {
        UIState.Success(it)
    }

    /**
     * Collect network request result with mapping
     *
     * @receiver [collectEither]
     */
    protected fun <T, S> Flow<Either<NetworkError, T>>.collectNetworkRequestWithMapping(state: MutableStateFlow<UIState<S>>, mapToUI: (T) -> S) = collectEither(state) {
        UIState.Success(mapToUI(it))
    }

    /**
     * Collect network request result and mapping [Either] to [UIState]
     *
     * @receiver [NetworkError] or [data][T] in [Flow] with [Either]
     *
     * @param T domain layer model
     * @param S presentation layer model
     * @param state [MutableStateFlow] with [UIState]
     *
     * @see viewModelScope
     * @see launch
     * @see [Flow.collect]
     */
    private fun <T, S> Flow<Either<NetworkError, T>>.collectEither(state: MutableStateFlow<UIState<S>>, successful: (T) -> UIState.Success<S>)
    {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = UIState.Loading()
            this@collectEither.collect {
                when (it)
                {
                    is Either.Left -> state.value = UIState.Error(it.value)
                    is Either.Right -> state.value = successful(it.value)
                }
            }
        }
    }
}