package dev.hawk0f.itutor.core.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hawk0f.itutor.core.domain.Either
import dev.hawk0f.itutor.core.domain.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Base class for all [ViewModel]s
 */
abstract class BaseViewModel<State, Intent, Effect> : ViewModel() {
    // UI state
    private val _state: MutableStateFlow<State> by lazy { MutableStateFlow(initialState) }
    val state: StateFlow<State> = _state

    // One-time effects (toasts, nav, etc.)
    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect

    /**
     * Отправка интента (события) в ViewModel.
     * Метод вызывается из UI и обрабатывается в [handleIntent].
     *
     * @param intent Событие (Intent), которое нужно обработать.
     */
    fun sendIntent(intent: Intent) {
        viewModelScope.launch {
            handleIntent(intent)
        }
    }

    /**
     * Обновляет текущее состояние, используя редьюсер.
     *
     * @param reducer Функция, которая принимает текущее состояние и возвращает новое.
     */
    protected fun setState(reducer: State.() -> State) {
        _state.value = _state.value.reducer()
    }

    /**
     * Отправляет одноразовый [Effect], например, навигацию или тост.
     *
     * @param builder Функция, создающая Effect.
     */
    protected fun sendEffect(builder: () -> Effect) {
        viewModelScope.launch {
            _effect.emit(builder())
        }
    }

    /**
     * Возвращает начальное состояние для ViewModel.
     *
     * @return Начальный [State].
     */
    protected abstract val initialState: State

    /**
     * Обработка поступающих интентов.
     * Обязательно реализуется в конкретной ViewModel.
     *
     * @param intent [Intent] для обработки.
     */
    protected abstract suspend fun handleIntent(intent: Intent)

    /**
     * Коллектит поток, обрабатывая состояния загрузки, успеха и ошибки.
     *
     * @param onLoading Опционально: функция, возвращающая новое состояние при начале загрузки.
     * @param onSuccess Обработка успешного результата: функция, возвращающая новое состояние.
     * @param onError Обработка ошибки: функция, возвращающая новое состояние.
     */
    protected suspend fun <T> Flow<Either<NetworkError, T>>.collectEither(
        onLoading: (State.() -> State)? = null,
        onSuccess: State.(T) -> State,
        onError: (NetworkError) -> Effect
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (onLoading != null) {
                setState(onLoading)
            }

            this@collectEither.collect { result ->
                when (result) {
                    is Either.Left -> sendEffect { onError(result.value) }
                    is Either.Right -> setState { onSuccess(result.value) }
                }
            }
        }
    }

    /**
     * Коллектит поток [Flow<Either<NetworkError, T>>], сначала отображая результат в [R],
     * затем обрабатывая как успех или ошибку.
     *
     * @param onLoading Опционально: функция, возвращающая новое состояние при начале загрузки.
     * @param mapper Маппинг результата T → R.
     * @param onSuccess Обработка успешного результата: функция, возвращающая новое состояние.
     * @param onError Обработка ошибки: функция, возвращающая новое состояние.
     */
    protected suspend fun <T, R> Flow<Either<NetworkError, T>>.collectMappedEither(
        onLoading: (State.() -> State)? = null,
        mapper: (T) -> R,
        onSuccess: State.(R) -> State,
        onError: (NetworkError) -> Effect
    ) {
        collectEither(
            onLoading = onLoading,
            onSuccess = { onSuccess(mapper(it)) },
            onError = onError
        )
    }
}