package dev.hawk0f.itutor.profile.presentation.ui

import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hawk0f.itutor.auth.data.models.UserDTO
import dev.hawk0f.itutor.auth.presentation.models.UserUI
import dev.hawk0f.itutor.auth.presentation.models.toUi
import dev.hawk0f.itutor.core.R
import dev.hawk0f.itutor.core.data.local.UserDataPreferences
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateEmail
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateName
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidatePhone
import dev.hawk0f.itutor.profile.domain.usecases.GetUserByIdUseCase
import dev.hawk0f.itutor.profile.domain.usecases.UpdateUserUseCase
import javax.inject.Inject

data class ProfileState(
    val isLoading: Boolean = false,
    val name: String = "",
    val surname: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = "",
)

sealed class ProfileIntent {
    data object FetchUserProfile : ProfileIntent()
    data object BackButton : ProfileIntent()
    data class EmailChanged(val email: String) : ProfileIntent()
    data class PhoneNumberChanged(val phoneNumber: String) : ProfileIntent()
    data class PasswordChanged(val password: String) : ProfileIntent()
    data class NameChanged(val name: String) : ProfileIntent()
    data class SurnameChanged(val surname: String) : ProfileIntent()
    data object SaveButton : ProfileIntent()
}

sealed class ProfileEffect {
    data object Back : ProfileEffect()
    data class ShowMessage(val message: String) : ProfileEffect()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    val userDataPreferences: UserDataPreferences,
    private val validateName: ValidateName,
    private val validateEmail: ValidateEmail,
    private val validatePhone: ValidatePhone,
) : BaseViewModel<ProfileState, ProfileIntent, ProfileEffect>() {

    fun init(user: UserUI) {
        setState {
            copy(
                name = user.name,
                surname = user.surname,
                email = user.email,
                phoneNumber = user.phoneNumber,
                password = user.password
            )
        }
    }

    override val initialState: ProfileState = ProfileState()

    override suspend fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.BackButton -> sendEffect { ProfileEffect.Back }
            is ProfileIntent.EmailChanged -> setState { copy(email = intent.email) }
            is ProfileIntent.NameChanged -> setState { copy(name = intent.name) }
            is ProfileIntent.PasswordChanged -> setState { copy(password = intent.password) }
            is ProfileIntent.PhoneNumberChanged -> setState { copy(phoneNumber = intent.phoneNumber) }
            ProfileIntent.SaveButton -> {
                val validateName = validateName(state.value.name)
                val validateEmail = validateEmail(state.value.email)
                val validatePhone = validatePhone(state.value.phoneNumber)

                if (validateName.isSuccessful && validateEmail.isSuccessful && validatePhone.isSuccessful) {
                    updateUserUseCase(
                        UserDTO(
                            CurrentUser.getUserId(),
                            state.value.name,
                            state.value.surname,
                            state.value.email,
                            state.value.phoneNumber,
                            state.value.password
                        )
                    ).collectEither(
                        onLoading = { copy(isLoading = true) },
                        onSuccess = {
                            sendEffect { ProfileEffect.ShowMessage(context.getString(R.string.success_data_updated)) }
                            copy(
                                name = name,
                                surname = surname,
                                phoneNumber = phoneNumber,
                                email = email,
                                password = password
                            )
                        },
                        onError = {
                            ProfileEffect.ShowMessage(it.getErrorMessage(context))
                        }
                    )
                }
            }

            is ProfileIntent.SurnameChanged -> setState { copy(surname = intent.surname) }
            ProfileIntent.FetchUserProfile -> {
                getUserByIdUseCase(CurrentUser.getUserId()).collectMappedEither(
                    onLoading = { copy(isLoading = true) },
                    mapper = { it.toUi() },
                    onSuccess = {
                        copy(
                            name = name,
                            surname = surname,
                            phoneNumber = phoneNumber,
                            email = email,
                            password = password
                        )
                    },
                    onError = {
                        ProfileEffect.ShowMessage(it.getErrorMessage(context))
                    }
                )
            }
        }
    }
}