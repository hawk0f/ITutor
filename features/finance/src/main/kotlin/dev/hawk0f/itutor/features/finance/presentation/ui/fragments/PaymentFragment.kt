package dev.hawk0f.itutor.features.finance.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.base.BaseHorizontalDividerItemDecoration
import dev.hawk0f.itutor.core.presentation.base.BaseVerticalDividerItemDecoration
import dev.hawk0f.itutor.features.finance.R
import dev.hawk0f.itutor.features.finance.databinding.FragmentPaymentBinding
import dev.hawk0f.itutor.features.finance.presentation.ui.adapters.DatePaymentsAdapter
import dev.hawk0f.itutor.features.finance.presentation.ui.viewmodels.PaymentViewModel
import jp.wasabeef.recyclerview.animators.FadeInAnimator

@AndroidEntryPoint
class PaymentFragment : BaseFragment<PaymentViewModel, FragmentPaymentBinding>(R.layout.fragment_payment)
{
    override val viewModel: PaymentViewModel by viewModels()
    override val binding: FragmentPaymentBinding by viewBinding(FragmentPaymentBinding::bind)

    private val datePaymentsAdapter = DatePaymentsAdapter { studentId, lessonId, hasPaid ->
        viewModel.updatePayment(studentId, lessonId, hasPaid)
    }

    override fun initialize()
    {
        setupRecycler()
    }

    private fun setupRecycler() = with(binding) {
        with(recyclerDateTransactions) {
            layoutManager = LinearLayoutManager(context)
            adapter = datePaymentsAdapter

            itemAnimator = FadeInAnimator()

            addItemDecoration(BaseHorizontalDividerItemDecoration(40))
            addItemDecoration(BaseVerticalDividerItemDecoration(30, 10))
        }
    }

    override fun setupRequests()
    {
        fetchPayments()
    }

    private fun fetchPayments()
    {
        viewModel.fetchPayments()
    }

    override fun setupSubscribers()
    {
        subscribeToPayments()
        subscribeToUpdate()
    }

    private fun subscribeToPayments() = with(binding) {
        viewModel.lessonStudentsState.collectAsUIState(state = {
            it.setupViewVisibilityLinear(group, loader)
        }, onSuccess = {
            datePaymentsAdapter.submitList(it)
        })
    }

    private fun subscribeToUpdate()
    {
        viewModel.updateState.collectAsUIState { /* showToastLong("Статус обновлён") */ }
    }
}