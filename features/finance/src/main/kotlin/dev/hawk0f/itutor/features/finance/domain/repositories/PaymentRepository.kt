package dev.hawk0f.itutor.features.finance.domain.repositories

import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.LessonStudent

interface PaymentRepository
{
    fun fetchLessonStudents(userId: Int): RemoteWrapper<List<LessonStudent>>

    fun updateLessonStudentPaymentStatus(studentId: Int, lessonId: Int, hasPaid: Boolean): RemoteWrapper<Unit>
}