package dev.hawk0f.itutor.finance.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.finance.domain.usecases.FetchPaymentsUseCase
import dev.hawk0f.itutor.finance.domain.usecases.UpdatePaymentStatusUseCase
import dev.hawk0f.itutor.student.presentation.models.StudentUI
import javax.inject.Inject

data class PaymentState(
    val students: List<StudentUI> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class PaymentIntent {
    data class StudentClick(val studentId: Int) : PaymentIntent()
    data class StudentDeleteClick(val studentId: Int) : PaymentIntent()
    data object AddStudentClick : PaymentIntent()
    data object FetchStudents : PaymentIntent()
}

sealed class PaymentEffect {
    data class NavigateToEditStudent(val studentId: Int) : PaymentEffect()
    data object NavigateToAddStudent : PaymentEffect()
    data class ShowMessage(val message: String) : PaymentEffect()
}

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val fetchPaymentsUseCase: FetchPaymentsUseCase,
    private val updatePaymentUseCase: UpdatePaymentStatusUseCase
) : BaseViewModel<PaymentState, PaymentIntent, PaymentEffect>() {
//    private val _lessonStudentsState = MutableUIStateFlow<List<DatePaymentsUI>>()
//    val lessonStudentsState = _lessonStudentsState.asStateFlow()
//
//    private val _updateState = MutableUIStateFlow<Unit>()
//    val updateState = _updateState.asStateFlow()

    //    fun fetchPayments() = fetchPaymentsUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_lessonStudentsState) { list ->
//        val datePaymentsList = ArrayList<DatePaymentsUI>()
//        list.forEach { payment ->
//            val paymentUI = payment.toUi()
//            val currentDatePayments = datePaymentsList.firstOrNull { it.date == paymentUI.parsedDate }
//            if (currentDatePayments != null)
//            {
//                currentDatePayments.payments.add(paymentUI)
//            }
//            else
//            {
//                val newDateLessons = DatePaymentsUI(
//                    paymentUI.parsedDate,
//                    payments = ArrayList<LessonStudentUI>().apply {
//                        add(paymentUI)
//                    })
//                datePaymentsList.add(newDateLessons)
//            }
//        }
//        datePaymentsList
//    }
//
//    fun updatePayment(studentId: Int, lessonId: Int, hasPaid: Boolean) = updatePaymentUseCase(studentId, lessonId, hasPaid).collectNetworkRequest(_updateState)
    override val initialState: PaymentState = PaymentState()

    override suspend fun handleIntent(intent: PaymentIntent) {
        when (intent) {
            else -> {}
        }
    }
}