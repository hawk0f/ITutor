package dev.hawk0f.itutor.features.finance.presentation.ui.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hawk0f.itutor.core.domain.CurrentUser
import dev.hawk0f.itutor.core.presentation.MutableUIStateFlow
import dev.hawk0f.itutor.core.presentation.base.BaseViewModel
import dev.hawk0f.itutor.core.presentation.models.PaymentUI
import dev.hawk0f.itutor.core.presentation.models.toUi
import dev.hawk0f.itutor.features.finance.domain.usecases.FetchPaymentsUseCase
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DiagramViewModel @Inject constructor(private val fetchPaymentsUseCase: FetchPaymentsUseCase, private val currentUser: CurrentUser) : BaseViewModel()
{
    private val _paymentState = MutableUIStateFlow<List<PaymentUI>>()
    val paymentState = _paymentState.asStateFlow()

    private val payments = ArrayList<PaymentUI>()

    fun setPayments(newPayments: List<PaymentUI>)
    {
        payments.clear()
        payments.addAll(newPayments)
    }

    fun getPayments() : List<PaymentUI>
    {
        return payments
    }

    fun fetchPayments() = fetchPaymentsUseCase(currentUser.getUserId()).collectNetworkRequestWithMapping(_paymentState) { list ->
        list.map { it.toUi() }
    }
}