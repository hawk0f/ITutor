package dev.hawk0f.itutor.finance.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.finance.domain.usecases.FetchPaymentsUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import javax.inject.Inject

data class DiagramState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class DiagramIntent {
    data class StudentClick(val studentId: Int) : DiagramIntent()
    data class StudentDeleteClick(val studentId: Int) : DiagramIntent()
    data object AddStudentClick : DiagramIntent()
    data object FetchStudents : DiagramIntent()
}

sealed class DiagramEffect {
    data class NavigateToEditStudent(val studentId: Int) : DiagramEffect()
    data object NavigateToAddStudent : DiagramEffect()
    data class ShowMessage(val message: String) : DiagramEffect()
}

@HiltViewModel
class DiagramViewModel @Inject constructor(private val fetchPaymentsUseCase: FetchPaymentsUseCase) :
    BaseViewModel<DiagramState, DiagramIntent, DiagramEffect>() {
    //    private val _paymentState = MutableUIStateFlow<List<LessonStudentUI>>()
//    val paymentState = _paymentState.asStateFlow()
//
//    private val payments = ArrayList<LessonStudentUI>()
//
//    fun setPayments(newPayments: List<LessonStudentUI>)
//    {
//        payments.clear()
//        payments.addAll(newPayments)
//    }
//
//    fun getPayments() : List<LessonStudentUI>
//    {
//        return payments
//    }
//
//    fun fetchPayments() = fetchPaymentsUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_paymentState) { list ->
//        list.map { it.toUi() }
//    }
    override val initialState: DiagramState = DiagramState()

    override suspend fun handleIntent(intent: DiagramIntent) {
        when (intent) {
            else -> {}
        }
    }
}