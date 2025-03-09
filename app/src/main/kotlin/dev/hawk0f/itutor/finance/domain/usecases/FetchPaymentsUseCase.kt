package dev.hawk0f.itutor.finance.domain.usecases

import dev.hawk0f.itutor.finance.domain.repositories.PaymentRepository
import javax.inject.Inject

class FetchPaymentsUseCase @Inject constructor(private val repository: PaymentRepository)
{
    operator fun invoke(userId: Int) = repository.fetchLessonStudents(userId)
}