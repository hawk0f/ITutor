package dev.hawk0f.itutor.features.auth.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.local.UserDataPreferences
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.UserUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateEmail
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidatePassword
import dev.hawk0f.itutor.features.auth.domain.usecases.AuthUserUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(val userDataPreferences: UserDataPreferences, private val authUserUseCase: AuthUserUseCase, val validateEmail: ValidateEmail, val validatePassword: ValidatePassword) : BaseViewModel()
{
    var email = ""
    var password = ""

    private val _authState = MutableUIStateFlow<UserUI>()
    val authState = _authState.asStateFlow()

    fun authUser() = authUserUseCase(email, password).collectNetworkRequestWithMapping(_authState) {
        it.toUi()
    }

    fun clearFields()
    {
        email = ""
        password = ""
    }
}