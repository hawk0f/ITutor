package dev.hawk0f.itutor.student.presentation.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.student.domain.usecases.DeleteStudentUseCase
import dev.hawk0f.itutor.student.domain.usecases.FetchStudentsUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import dev.hawk0f.itutor.student.presentation.models.toUi
import dev.hawk0f.itutor.student.presentation.ui.main.StudentsEffect.NavigateToAddStudent
import dev.hawk0f.itutor.student.presentation.ui.main.StudentsEffect.NavigateToEditStudent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StudentsState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class StudentsIntent {
    data class StudentClick(val studentId: Int) : StudentsIntent()
    data class StudentDeleteClick(val studentId: Int) : StudentsIntent()
    data object AddStudentClick : StudentsIntent()
    data object FetchStudents : StudentsIntent()
}

sealed class StudentsEffect {
    data class NavigateToEditStudent(val studentId: Int) : StudentsEffect()
    data object NavigateToAddStudent : StudentsEffect()
    data class ShowMessage(val message: String) : StudentsEffect()
}

@HiltViewModel
class StudentsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fetchStudentsUseCase: FetchStudentsUseCase,
    private val deleteStudentUseCase: DeleteStudentUseCase
) : BaseViewModel<StudentsState, StudentsIntent, StudentsEffect>() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            handleIntent(StudentsIntent.FetchStudents)
        }
    }

    override val initialState: StudentsState = StudentsState()

    override suspend fun handleIntent(intent: StudentsIntent) {
        when (intent) {
            is StudentsIntent.StudentClick -> sendEffect {
                NavigateToEditStudent(
                    intent.studentId
                )
            }

            is StudentsIntent.StudentDeleteClick -> {
                deleteStudentUseCase(intent.studentId).collectEither(
                    onLoading = { copy(isLoading = true) },
                    onSuccess = {
                        copy(
                            isLoading = false,
                            students = students.filter { it.id != intent.studentId })
                    },
                    onError = {
                        setState { copy(isLoading = false) }
                        StudentsEffect.ShowMessage(it.getErrorMessage(context))
                    }
                )
            }

            StudentsIntent.AddStudentClick -> sendEffect {
                NavigateToAddStudent
            }

            StudentsIntent.FetchStudents -> {
                fetchStudentsUseCase(CurrentUser.getUserId()).collectMappedEither(
                    onLoading = { copy(isLoading = true) },
                    mapper = { it.map { it.toUi() } },
                    onSuccess = { copy(isLoading = false, students = it) },
                    onError = {
                        setState { copy(isLoading = false) }
                        StudentsEffect.ShowMessage(it.getErrorMessage(context))
                    }
                )
            }
        }
    }
}