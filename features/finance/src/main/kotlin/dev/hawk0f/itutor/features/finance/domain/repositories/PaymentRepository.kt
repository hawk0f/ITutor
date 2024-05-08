package dev.hawk0f.itutor.features.finance.domain.repositories

import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.Payment

interface PaymentRepository
{
    fun fetchPayments(userId: Int): RemoteWrapper<List<Payment>>

    fun updatePayment(studentId: Int, lessonId: Int, hasPaid: Boolean): RemoteWrapper<Unit>
}