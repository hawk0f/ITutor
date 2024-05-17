package dev.hawk0f.itutor.features.finance.data.repositories

import dev.hawk0f.itutor.core.data.apiservices.LessonStudentService
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.LessonStudent
import dev.hawk0f.itutor.features.finance.domain.repositories.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(private val lessonStudentService: LessonStudentService) : BaseRepository(), PaymentRepository
{
    override fun fetchLessonStudents(userId: Int): RemoteWrapper<List<LessonStudent>> = doNetworkRequestForList {
        lessonStudentService.fetchLessonStudents(userId)
    }

    override fun updateLessonStudentPaymentStatus(studentId: Int, lessonId: Int, hasPaid: Boolean): RemoteWrapper<Unit> = doNetworkRequestUnit {
        lessonStudentService.updateLessonStudentPaymentStatus(studentId, lessonId, hasPaid)
    }
}