package dev.hawk0f.itutor.auth.presentation.ui

import android.app.Application
import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hawk0f.itutor.auth.domain.usecases.AuthUserUseCase
import dev.hawk0f.itutor.auth.presentation.models.toUi
import dev.hawk0f.itutor.core.data.local.UserDataPreferences
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateEmail
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidatePassword
import javax.inject.Inject

data class AuthState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
)

sealed class AuthIntent {
    data object AuthButton : AuthIntent()
    data class EmailChanged(val email: String) : AuthIntent()
    data class PasswordChanged(val password: String) : AuthIntent()
    data object RegButton : AuthIntent()
    data object BackButton : AuthIntent()
}

sealed class AuthEffect {
    data object Exit : AuthEffect()
    data object NavigateToReg : AuthEffect()
    data object NavigateToMain : AuthEffect()
    data class ShowMessage(val message: String) : AuthEffect()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val userDataPreferences: UserDataPreferences,
    private val authUserUseCase: AuthUserUseCase,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
) : BaseViewModel<AuthState, AuthIntent, AuthEffect>() {

    override val initialState: AuthState = AuthState()

    override suspend fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.AuthButton -> {
                val validateEmailResult = validateEmail(state.value.email)
                val validatePasswordResult = validatePassword(state.value.password)
                if (validateEmailResult.isSuccessful && validatePasswordResult.isSuccessful) {
                    authUserUseCase(
                        state.value.email,
                        state.value.password
                    ).collectMappedEither(
                        onLoading = { copy(isLoading = true) },
                        mapper = { it.toUi() },
                        onSuccess = {
                            userDataPreferences.userId = it.id
                            sendEffect { AuthEffect.NavigateToMain }
                            copy()
                        },
                        onError = {
                            AuthEffect.ShowMessage(it.getErrorMessage(context))
                        }
                    )
                } else {
                    sendEffect {
                        AuthEffect.ShowMessage(
                            validateEmailResult.errorMessage
                                ?: validatePasswordResult.errorMessage!!
                        )
                    }
                }
            }

            is AuthIntent.RegButton -> sendEffect { AuthEffect.NavigateToReg }
            is AuthIntent.BackButton -> sendEffect { AuthEffect.Exit }
            is AuthIntent.EmailChanged -> setState { copy(email = intent.email) }
            is AuthIntent.PasswordChanged -> setState { copy(password = intent.password) }
        }
    }
}