package dev.hawk0f.itutor.homework.data.repositories

import dev.hawk0f.itutor.finance.data.api.LessonStudentService
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.finance.domain.models.LessonStudent
import dev.hawk0f.itutor.homework.domain.repositories.HomeworkRepository
import javax.inject.Inject

class HomeworkRepositoryImpl @Inject constructor(
    private val lessonStudentService: LessonStudentService
) : BaseRepository(), HomeworkRepository
{
    override fun fetchLessonStudents(userId: Int): RemoteWrapper<List<LessonStudent>> = doNetworkRequestForList {
        lessonStudentService.fetchLessonStudents(userId)
    }

    override fun addHomework(studentId: Int, lessonId: Int, homework: String): RemoteWrapper<Unit> = doNetworkRequestUnit {
        lessonStudentService.addHomework(studentId, lessonId, homework)
    }

    override fun updateHomework(studentId: Int, lessonId: Int, homework: String): RemoteWrapper<Unit> = doNetworkRequestUnit {
        lessonStudentService.updateHomework(studentId, lessonId, homework)
    }

    override fun updateHomeworkStatus(studentId: Int, lessonId: Int, isHomeworkDone: Boolean): RemoteWrapper<Unit> = doNetworkRequestUnit {
        lessonStudentService.updateHomeworkStatus(studentId, lessonId, isHomeworkDone)
    }
}