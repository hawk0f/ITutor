package dev.hawk0f.itutor.features.students.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.models.StudentDTO
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateIsEmpty
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateName
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidatePhone
import dev.hawk0f.itutor.features.students.domain.usecases.AddStudentUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddStudentViewModel @Inject constructor(private val addStudentUseCase: AddStudentUseCase, val validateName: ValidateName, val validateIsEmpty: ValidateIsEmpty, val validatePhone: ValidatePhone) : BaseViewModel()
{
    var name = ""
    var surname = ""
    var age = ""
    var phoneNumber = ""
    var singlePrice = ""
    var groupPrice = ""
    var note = ""

    private val _addState = MutableUIStateFlow<Unit>()
    val addState = _addState.asStateFlow()

    fun addStudent() = addStudentUseCase(
        StudentDTO(
            name = name.trim(),
            surname = surname.trim(),
            age = age.trim().toInt(),
            phoneNumber = phoneNumber,
            singlePrice = singlePrice.trim().toDouble(),
            groupPrice = groupPrice.trim().toDouble(),
            note = note.trim(),
            userId = CurrentUser.getUserId()
        )).collectNetworkRequest(_addState)

    fun clearFields()
    {
        name = ""
        surname = ""
        age = ""
        phoneNumber = ""
        singlePrice = ""
        groupPrice = ""
        note = ""
    }
}