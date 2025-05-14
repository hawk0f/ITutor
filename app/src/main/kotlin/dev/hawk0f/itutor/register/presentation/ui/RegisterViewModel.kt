package dev.hawk0f.itutor.register.presentation.ui

import android.app.Application
import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hawk0f.itutor.auth.data.models.UserDTO
import dev.hawk0f.itutor.auth.presentation.models.toUi
import dev.hawk0f.itutor.core.data.local.UserDataPreferences
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateEmail
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateName
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidatePassword
import dev.hawk0f.itutor.register.domain.usecases.RegisterUserUseCase
import javax.inject.Inject

data class RegState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val surname: String = "",
)

sealed class RegIntent {
    data object AuthButton : RegIntent()
    data class EmailChanged(val email: String) : RegIntent()
    data class PasswordChanged(val password: String) : RegIntent()
    data class NameChanged(val name: String) : RegIntent()
    data class SurnameChanged(val surname: String) : RegIntent()
    data object RegButton : RegIntent()
}

sealed class RegEffect {
    data object Back : RegEffect()
    data object NavigateToMain : RegEffect()
    data class ShowMessage(val message: String) : RegEffect()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val userDataPreferences: UserDataPreferences,
    private val registerUserUseCase: RegisterUserUseCase,
    val validateName: ValidateName,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword
) : BaseViewModel<RegState, RegIntent, RegEffect>() {
    override val initialState: RegState = RegState()

    override suspend fun handleIntent(intent: RegIntent) {
        when (intent) {
            RegIntent.AuthButton -> {
                sendEffect { RegEffect.Back }
            }

            is RegIntent.EmailChanged -> {
                setState { copy(email = intent.email) }
            }

            is RegIntent.NameChanged -> {
                setState { copy(name = intent.name) }
            }

            is RegIntent.PasswordChanged -> {
                setState { copy(password = intent.password) }
            }

            RegIntent.RegButton -> {
                val validateNameResult = validateName(state.value.name)
                val validateEmailResult = validateEmail(state.value.email)
                val validatePasswordResult = validatePassword(state.value.password)
                if (validateNameResult.isSuccessful && validateEmailResult.isSuccessful && validatePasswordResult.isSuccessful) {
                    registerUserUseCase(
                        UserDTO(
                            email = state.value.email,
                            password = state.value.password,
                            name = state.value.name,
                            surname = state.value.surname
                        )
                    ).collectMappedEither(
                        onLoading = { copy(isLoading = true) },
                        mapper = { it.toUi() },
                        onSuccess = {
                            userDataPreferences.userId = it.id
                            sendEffect { RegEffect.NavigateToMain }
                            copy()
                        },
                        onError = {
                            RegEffect.ShowMessage(it.getErrorMessage(context))
                        }
                    )
                } else {
                    sendEffect {
                        RegEffect.ShowMessage(
                            validateNameResult.errorMessage ?: validateEmailResult.errorMessage
                            ?: validatePasswordResult.errorMessage!!
                        )
                    }
                }
            }

            is RegIntent.SurnameChanged -> {
                setState { copy(surname = intent.surname) }
            }
        }
    }
}