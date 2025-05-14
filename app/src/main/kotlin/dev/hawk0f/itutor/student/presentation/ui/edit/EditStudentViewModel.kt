package dev.hawk0f.itutor.student.presentation.ui.edit

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
import dev.hawk0f.itutor.student.domain.usecases.UpdateStudentUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import javax.inject.Inject

data class EditStudentState(
    val id: Int = 0,
    val name: String = "",
    val surname: String = "",
    val age: Int = 0,
    val phoneNumber: String = "",
    val singlePrice: Double = 0.0,
    val groupPrice: Double = 0.0,
    val note: String = "",
    val isLoading: Boolean = false,
)

sealed class EditStudentIntent {
    data class NameChanged(val name: String) : EditStudentIntent()
    data class SurnameChanged(val surname: String) : EditStudentIntent()
    data class AgeChanged(val age: Int) : EditStudentIntent()
    data class PhoneNumberChanged(val phoneNumber: String) : EditStudentIntent()
    data class SinglePriceChanged(val singlePrice: Double) : EditStudentIntent()
    data class GroupPriceChanged(val groupPrice: Double) : EditStudentIntent()
    data class NoteChanged(val note: String) : EditStudentIntent()
    data object BackButtonClick : EditStudentIntent()
    data object UpdateButtonClick : EditStudentIntent()
}

sealed class EditStudentEffect {
    data object BackToMain : EditStudentEffect()
    data class ShowMessage(val message: String) : EditStudentEffect()
}

@HiltViewModel
class EditStudentViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val validateName: ValidateName,
    private val validateAge: ValidateAge,
    private val validatePhone: ValidatePhone,
    private val validatePrice: ValidatePrice,
    private val updateStudentUseCase: UpdateStudentUseCase
) : BaseViewModel<EditStudentState, EditStudentIntent, EditStudentEffect>() {

    fun init(student: StudentUI) {
        setState {
            copy(
                id = student.id,
                name = student.fullName.split(' ')[0],
                surname = student.fullName.split(' ')[1],
                age = student.age,
                phoneNumber = student.phoneNumber,
                singlePrice = student.singlePrice,
                groupPrice = student.groupPrice,
                note = student.note,
            )
        }
    }

    override val initialState: EditStudentState = EditStudentState()

    override suspend fun handleIntent(intent: EditStudentIntent) {
        when (intent) {
            is EditStudentIntent.AgeChanged -> setState { copy(age = intent.age) }
            EditStudentIntent.BackButtonClick -> sendEffect { EditStudentEffect.BackToMain }
            EditStudentIntent.UpdateButtonClick -> {
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
                    updateStudentUseCase(
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
                            sendEffect { EditStudentEffect.BackToMain }
                            copy()
                        },
                        onError = {
                            EditStudentEffect.ShowMessage(it.getErrorMessage(context))
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

            is EditStudentIntent.GroupPriceChanged -> setState { copy(groupPrice = intent.groupPrice) }
            is EditStudentIntent.NameChanged -> setState { copy(name = intent.name) }
            is EditStudentIntent.NoteChanged -> setState { copy(note = intent.note) }
            is EditStudentIntent.PhoneNumberChanged -> setState { copy(phoneNumber = intent.phoneNumber) }
            is EditStudentIntent.SinglePriceChanged -> setState { copy(singlePrice = intent.singlePrice) }
            is EditStudentIntent.SurnameChanged -> setState { copy(surname = intent.surname) }
        }
    }
}