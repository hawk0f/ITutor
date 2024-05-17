package dev.hawk0f.itutor.features.homework.data.repositories

import dev.hawk0f.itutor.core.data.apiservices.LessonStudentService
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.LessonStudent
import dev.hawk0f.itutor.features.homework.domain.repositories.HomeworkRepository
import javax.inject.Inject

class HomeworkRepositoryImpl @Inject constructor(private val lessonStudentService: LessonStudentService) : BaseRepository(), HomeworkRepository
{
    override fun fetchLessonStudents(userId: Int): RemoteWrapper<List<LessonStudent>> = doNetworkRequestForList {
        lessonStudentService.fetchLessonStudents(userId)
    }

    override fun addHomework(studentId: Int, lessonId: Int, homework: String): RemoteWrapper<Unit> = doNetworkRequestUnit {
        lessonStudentService.addHomework(studentId, lessonId, homework)
    }

    override fun updateHomeworkStatus(studentId: Int, lessonId: Int, isHomeworkDone: Boolean): RemoteWrapper<Unit> = doNetworkRequestUnit {
        lessonStudentService.updateHomeworkStatus(studentId, lessonId, isHomeworkDone)
    }
}