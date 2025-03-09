package dev.hawk0f.itutor.student.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.student.domain.usecases.DeleteStudentUseCase
import dev.hawk0f.itutor.student.domain.usecases.FetchStudentsUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import dev.hawk0f.itutor.student.presentation.models.toUi
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(private val fetchStudentsUseCase: FetchStudentsUseCase, private val deleteStudentUseCase: DeleteStudentUseCase) : BaseViewModel()
{
    private val _studentState = MutableUIStateFlow<List<StudentUI>>()
    val studentState = _studentState.asStateFlow()

    private val _deleteState = MutableUIStateFlow<Unit>()
    val deleteState = _deleteState.asStateFlow()

    fun fetchStudents() = fetchStudentsUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_studentState) { list ->
        list.map { it.toUi((list.indexOf(it) + 1).toString()) }
    }

    fun deleteStudent(studentId: Int) = deleteStudentUseCase(studentId).collectNetworkRequest(_deleteState)
}