package dev.hawk0f.itutor.features.auth.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.UserUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.features.auth.domain.usecases.AuthUserUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authUserUseCase: AuthUserUseCase) : BaseViewModel()
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