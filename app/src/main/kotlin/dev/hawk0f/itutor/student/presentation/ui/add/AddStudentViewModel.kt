package dev.hawk0f.itutor.student.presentation.ui.add

import android.app.Application
import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateAge
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateName
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidatePhone
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidatePrice
import dev.hawk0f.itutor.student.data.models.StudentDTO
import dev.hawk0f.itutor.student.domain.usecases.AddStudentUseCase
import javax.inject.Inject

data class AddStudentState(
    val name: String = "",
    val surname: String = "",
    val age: Int = 0,
    val phoneNumber: String = "",
    val singlePrice: Double = 0.0,
    val groupPrice: Double = 0.0,
    val note: String = "",
    val isLoading: Boolean = false,
)

sealed class AddStudentIntent {
    data class NameChanged(val name: String) : AddStudentIntent()
    data class SurnameChanged(val surname: String) : AddStudentIntent()
    data class AgeChanged(val age: Int) : AddStudentIntent()
    data class PhoneNumberChanged(val phoneNumber: String) : AddStudentIntent()
    data class SinglePriceChanged(val singlePrice: Double) : AddStudentIntent()
    data class GroupPriceChanged(val groupPrice: Double) : AddStudentIntent()
    data class NoteChanged(val note: String) : AddStudentIntent()
    data object BackButtonClick : AddStudentIntent()
    data object AddButtonClick : AddStudentIntent()
}

sealed class AddStudentEffect {
    data object BackToMain : AddStudentEffect()
    data class ShowMessage(val message: String) : AddStudentEffect()
}

@HiltViewModel
class AddStudentViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val addStudentUseCase: AddStudentUseCase,
    private val validateName: ValidateName,
    private val validateAge: ValidateAge,
    private val validatePhone: ValidatePhone,
    private val validatePrice: ValidatePrice
) : BaseViewModel<AddStudentState, AddStudentIntent, AddStudentEffect>() {

    override val initialState: AddStudentState = AddStudentState()

    override suspend fun handleIntent(intent: AddStudentIntent) {
        when (intent) {
            is AddStudentIntent.NameChanged -> setState { copy(name = intent.name) }
            is AddStudentIntent.SurnameChanged -> setState { copy(surname = intent.surname) }
            is AddStudentIntent.AgeChanged -> setState { copy(age = intent.age) }
            is AddStudentIntent.GroupPriceChanged -> setState { copy(groupPrice = intent.groupPrice) }
            is AddStudentIntent.NoteChanged -> setState { copy(note = intent.note) }
            is AddStudentIntent.PhoneNumberChanged -> setState { copy(phoneNumber = intent.phoneNumber) }
            is AddStudentIntent.SinglePriceChanged -> setState { copy(singlePrice = intent.singlePrice) }
            AddStudentIntent.AddButtonClick -> {
                val validateName = validateName(state.value.name)
                val validateAge = validateAge(state.value.age.toString())
                val validatePhone = validatePhone(state.value.phoneNumber)
                val validateSinglePrice = validatePrice(state.value.singlePrice.toString())
                val validateGroupPrice = validatePrice(state.value.groupPrice.toString())

                if (validateName.isSuccessful &&
                    validateAge.isSuccessful &&
                    validatePhone.isSuccessful &&
                    validateSinglePrice.isSuccessful &&
                    validateGroupPrice.isSuccessful
                ) {
                    addStudentUseCase(
                        StudentDTO(
                            name = state.value.name,
                            surname = state.value.surname,
                            age = state.value.age,
                            phoneNumber = state.value.phoneNumber,
                            singlePrice = state.value.singlePrice,
                            groupPrice = state.value.groupPrice,
                            note = state.value.note,
                            userId = CurrentUser.getUserId()
                        )
                    ).collectEither(
                        onLoading = { copy(isLoading = true) },
                        onSuccess = {
                            sendEffect { AddStudentEffect.BackToMain }
                            copy()
                        },
                        onError = {
                            AddStudentEffect.ShowMessage(it.getErrorMessage(context))
                        }
                    )
                } else {
                    context.showToastLong(
                        validateName.errorMessage ?: validateAge.errorMessage
                        ?: validatePhone.errorMessage ?: validateSinglePrice.errorMessage
                        ?: validateGroupPrice.errorMessage!!
                    )
                }
            }

            AddStudentIntent.BackButtonClick -> sendEffect { AddStudentEffect.BackToMain }
        }
    }
}