package dev.hawk0f.itutor.features.students.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.models.StudentDTO
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.features.students.domain.usecases.AddStudentUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddStudentViewModel @Inject constructor(private val addStudentUseCase: AddStudentUseCase, private val currentUser: CurrentUser) : BaseViewModel()
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
            userId = currentUser.getUserId()
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