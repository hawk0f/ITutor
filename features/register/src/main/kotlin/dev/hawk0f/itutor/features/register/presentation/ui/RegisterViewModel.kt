package dev.hawk0f.itutor.features.register.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.models.UserDTO
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.UserUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateEmail
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateName
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidatePassword
import dev.hawk0f.itutor.features.register.domain.usecases.RegisterUserUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUserUseCase: RegisterUserUseCase, val validateName: ValidateName, val validateEmail: ValidateEmail, val validatePassword: ValidatePassword) : BaseViewModel()
{
    var name = ""
    var surname = ""
    var email = ""
    var password = ""

    private val _regState = MutableUIStateFlow<UserUI>()
    val regState = _regState.asStateFlow()

    fun registerUser() = registerUserUseCase(UserDTO(name = name, surname = surname, email = email, phoneNumber = null, password = password)).collectNetworkRequestWithMapping(_regState) {
        it.toUi()
    }

    fun clearFields()
    {
        email = ""
        password = ""
    }
}