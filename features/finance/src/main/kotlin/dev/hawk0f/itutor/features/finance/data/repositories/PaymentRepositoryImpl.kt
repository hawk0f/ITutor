package dev.hawk0f.itutor.features.finance.data.repositories

import dev.hawk0f.itutor.core.data.apiservices.PaymentService
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.Payment
import dev.hawk0f.itutor.features.finance.domain.repositories.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(private val paymentService: PaymentService) : BaseRepository(), PaymentRepository
{
    override fun fetchPayments(userId: Int): RemoteWrapper<List<Payment>> = doNetworkRequestForList {
        paymentService.fetchPayments(userId)
    }

    override fun updatePayment(studentId: Int, lessonId: Int, hasPaid: Boolean): RemoteWrapper<Unit> = doNetworkRequestUnit {
        paymentService.updatePayment(studentId, lessonId, hasPaid)
    }
}