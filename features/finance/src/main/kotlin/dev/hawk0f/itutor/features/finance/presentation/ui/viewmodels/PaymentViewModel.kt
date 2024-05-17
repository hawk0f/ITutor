package dev.hawk0f.itutor.features.finance.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.DatePaymentsUI
import dev.hawk0f.itutor.core.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.features.finance.domain.usecases.FetchPaymentsUseCase
import dev.hawk0f.itutor.features.finance.domain.usecases.UpdatePaymentStatusUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val fetchPaymentsUseCase: FetchPaymentsUseCase, private val updatePaymentUseCase: UpdatePaymentStatusUseCase, private val currentUser: CurrentUser) : BaseViewModel()
{
    private val _lessonStudentsState = MutableUIStateFlow<List<DatePaymentsUI>>()
    val lessonStudentsState = _lessonStudentsState.asStateFlow()

    private val _updateState = MutableUIStateFlow<Unit>()
    val updateState = _updateState.asStateFlow()

    fun fetchPayments() = fetchPaymentsUseCase(currentUser.getUserId()).collectNetworkRequestWithMapping(_lessonStudentsState) { list ->
        val datePaymentsList = ArrayList<DatePaymentsUI>()
        list.forEach { payment ->
            val paymentUI = payment.toUi()
            val currentDatePayments = datePaymentsList.firstOrNull { it.date == paymentUI.parsedDate }
            if (currentDatePayments != null)
            {
                currentDatePayments.payments.add(paymentUI)
            }
            else
            {
                val newDateLessons = DatePaymentsUI(paymentUI.parsedDate, payments = ArrayList<LessonStudentUI>().apply {
                        add(paymentUI)
                })
                datePaymentsList.add(newDateLessons)
            }
        }
        datePaymentsList
    }

    fun updatePayment(studentId: Int, lessonId: Int, hasPaid: Boolean) = updatePaymentUseCase(studentId, lessonId, hasPaid).collectNetworkRequest(_updateState)
}