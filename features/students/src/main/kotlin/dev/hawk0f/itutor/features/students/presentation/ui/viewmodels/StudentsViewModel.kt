package dev.hawk0f.itutor.features.students.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.StudentUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.features.students.domain.usecases.DeleteStudentUseCase
import dev.hawk0f.itutor.features.students.domain.usecases.FetchStudentsUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(private val fetchStudentsUseCase: FetchStudentsUseCase, private val deleteStudentUseCase: DeleteStudentUseCase, private val currentUser: CurrentUser) : BaseViewModel()
{
    private val _studentState = MutableUIStateFlow<List<StudentUI>>()
    val studentState = _studentState.asStateFlow()

    private val _deleteState = MutableUIStateFlow<Unit>()
    val deleteState = _deleteState.asStateFlow()

    fun fetchStudents() = fetchStudentsUseCase(currentUser.getUserId()).collectNetworkRequestWithMapping(_studentState) { list ->
        list.map { it.toUi() }
    }

    fun deleteStudent(studentId: Int) = deleteStudentUseCase(studentId).collectNetworkRequest(_deleteState)
}