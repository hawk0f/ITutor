package dev.hawk0f.itutor.features.finance.domain.usecases

import dev.hawk0f.itutor.features.finance.domain.repositories.PaymentRepository
import javax.inject.Inject

class FetchPaymentsUseCase @Inject constructor(private val repository: PaymentRepository)
{
    operator fun invoke(userId: Int) = repository.fetchPayments(userId)
}