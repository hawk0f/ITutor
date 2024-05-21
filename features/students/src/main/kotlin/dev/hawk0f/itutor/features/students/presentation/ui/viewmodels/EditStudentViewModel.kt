package dev.hawk0f.itutor.features.students.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.data.models.StudentDTO
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.StudentUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateIsEmpty
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateName
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidatePhone
import dev.hawk0f.itutor.features.students.domain.usecases.GetStudentByIdUseCase
import dev.hawk0f.itutor.features.students.domain.usecases.UpdateStudentUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditStudentViewModel @Inject constructor(private val getStudentByIdUseCase: GetStudentByIdUseCase, val validateName: ValidateName, val validateIsEmpty: ValidateIsEmpty, val validatePhone: ValidatePhone, private val updateStudentUseCase: UpdateStudentUseCase) : BaseViewModel()
{
    private var oldName = ""
    private var oldSurname = ""
    private var oldAge = ""
    private var oldPhoneNumber = ""
    private var oldSinglePrice = ""
    private var oldGroupPrice = ""
    private var oldNote = ""
    private var id = 0
    var name = ""
    var surname = ""
    var age = ""
    var phoneNumber = ""
    var singlePrice = ""
    var groupPrice = ""
    var note = ""
    private var userId = 0

    private val _studentState = MutableUIStateFlow<StudentUI>()
    val studentState = _studentState.asStateFlow()

    private val _updateState = MutableUIStateFlow<Unit>()
    val updateState = _updateState.asStateFlow()

    fun setStudent(student: StudentUI)
    {
        id = student.id
        name = student.fullName.split(' ')[0]
        surname = student.fullName.split(' ')[1]
        age = student.age.toString()
        phoneNumber = student.phoneNumber
        singlePrice = student.singlePrice.toInt().toString()
        groupPrice = student.groupPrice.toInt().toString()
        note = student.note
        userId = student.userId
        oldName = name
        oldSurname = surname
        oldAge = age
        oldPhoneNumber = phoneNumber
        oldSinglePrice = singlePrice
        oldGroupPrice = groupPrice
        oldNote = note
    }

    fun getStudentById(studentId: Int) = getStudentByIdUseCase(studentId).collectNetworkRequestWithMapping(_studentState) {
        it.toUi()
    }

    fun isUpdateNeeded() = oldName != name || oldSurname != surname || oldAge != age || oldPhoneNumber != phoneNumber || oldSinglePrice != singlePrice || oldGroupPrice != groupPrice || oldNote != note

    fun updateStudent() = updateStudentUseCase(
        StudentDTO(
            id = id,
            name = name.trim(),
            surname = surname.trim(),
            age = age.trim().toInt(),
            phoneNumber = phoneNumber.trim(),
            singlePrice = singlePrice.trim().toDouble(),
            groupPrice = groupPrice.trim().toDouble(),
            note = note.trim(),
            userId = userId
        )).collectNetworkRequest(_updateState)

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