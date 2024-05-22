package dev.hawk0f.itutor.features.profile.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.local.UserDataPreferences
import dev.hawk0f.itutor.core.data.models.UserDTO
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.UserUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateEmail
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateName
import dev.hawk0f.itutor.features.profile.domain.usecases.GetUserByIdUseCase
import dev.hawk0f.itutor.features.profile.domain.usecases.UpdateUserUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getUserByIdUseCase: GetUserByIdUseCase, private val updateUserUseCase: UpdateUserUseCase, val userDataPreferences: UserDataPreferences, val validateName: ValidateName, val validateEmail: ValidateEmail) : BaseViewModel()
{
    private var id: Int = 0
    private var oldName = ""
    private var oldSurname = ""
    private var oldEmail = ""
    var oldPhoneNumber = ""
    private var password = ""

    var name = ""
    var surname = ""
    var email = ""
    var phoneNumber = ""

    private val _userState = MutableUIStateFlow<UserUI>()
    val userState = _userState.asStateFlow()

    private val _updateState = MutableUIStateFlow<Unit>()
    val updateState = _updateState.asStateFlow()

    fun getUserById() = getUserByIdUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_userState) {
        it.toUi()
    }

    fun updateUser() = updateUserUseCase(UserDTO(id, name, surname, email, phoneNumber, password)).collectNetworkRequest(_updateState)

    fun isUpdateNeeded() = oldName != name || oldSurname != surname || oldEmail != email || oldPhoneNumber != phoneNumber

    fun setUser(user: UserUI)
    {
        id = user.id
        oldName = user.name
        oldSurname = user.surname
        oldEmail = user.email
        oldPhoneNumber = user.phoneNumber ?: ""
        name = user.name
        surname = user.surname
        email = user.email
        phoneNumber = user.phoneNumber ?: ""
        password = user.password
    }

    fun onUpdateSuccess()
    {
        oldName = name
        oldSurname = surname
        oldEmail = email
        oldPhoneNumber = phoneNumber
    }
}