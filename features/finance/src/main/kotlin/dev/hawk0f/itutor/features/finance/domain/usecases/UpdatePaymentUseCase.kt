package dev.hawk0f.itutor.features.finance.domain.usecases

import dev.hawk0f.itutor.features.finance.domain.repositories.PaymentRepository
import javax.inject.Inject

class UpdatePaymentUseCase @Inject constructor(private val repository: PaymentRepository)
{
    operator fun invoke(studentId: Int, lessonId: Int, hasPaid: Boolean) = repository.updatePayment(studentId, lessonId, hasPaid)
}