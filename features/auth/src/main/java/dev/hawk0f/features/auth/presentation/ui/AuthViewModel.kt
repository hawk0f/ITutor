package dev.hawk0f.features.auth.presentation.ui

import dev.hawk0f.core.presentation.MutableUIStateFlow
import dev.hawk0f.core.presentation.base.BaseViewModel
import dev.hawk0f.features.auth.domain.usecases.AuthUserUseCase
import dev.hawk0f.features.auth.presentation.models.UserUI
import dev.hawk0f.features.auth.presentation.models.toUi
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(private val authUserUseCase: AuthUserUseCase) : BaseViewModel()
{
    var email = ""
    var password = ""

    private val _authState = MutableUIStateFlow<UserUI>()
    val authState = _authState.asStateFlow()

    fun authUser() = authUserUseCase(email, password).collectNetworkRequest(_authState) {
        it.toUi()
    }

    fun clearFields()
    {
        email = ""
        password = ""
    }
}