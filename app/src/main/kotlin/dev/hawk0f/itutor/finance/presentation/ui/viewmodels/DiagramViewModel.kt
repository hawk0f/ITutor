package dev.hawk0f.itutor.finance.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.finance.domain.usecases.FetchPaymentsUseCase
import dev.hawk0f.itutor.finance.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.finance.presentation.models.toUi
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DiagramViewModel @Inject constructor(private val fetchPaymentsUseCase: FetchPaymentsUseCase) : BaseViewModel()
{
    private val _paymentState = MutableUIStateFlow<List<LessonStudentUI>>()
    val paymentState = _paymentState.asStateFlow()

    private val payments = ArrayList<LessonStudentUI>()

    fun setPayments(newPayments: List<LessonStudentUI>)
    {
        payments.clear()
        payments.addAll(newPayments)
    }

    fun getPayments() : List<LessonStudentUI>
    {
        return payments
    }

    fun fetchPayments() = fetchPaymentsUseCase(CurrentUser.getUserId()).collectNetworkRequestWithMapping(_paymentState) { list ->
        list.map { it.toUi() }
    }
}