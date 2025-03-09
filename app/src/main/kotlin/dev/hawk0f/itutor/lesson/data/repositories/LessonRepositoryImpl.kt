package dev.hawk0f.itutor.lesson.data.repositories

import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.lesson.domain.repositories.LessonRepository
import dev.hawk0f.itutor.lesson.data.api.LessonService
import dev.hawk0f.itutor.lesson.data.models.LessonDTO
import dev.hawk0f.itutor.lesson.domain.models.Lesson
import dev.hawk0f.itutor.lesson.domain.models.StudentInLesson
import javax.inject.Inject

class LessonRepositoryImpl @Inject constructor(private val lessonService: LessonService) : BaseRepository(),
                                                                                           LessonRepository
{
    override fun fetchLessons(userId: Int): RemoteWrapper<List<Lesson>> = doNetworkRequestForList {
        lessonService.fetchLessons(userId)
    }

    override fun fetchLessonStudents(userId: Int): RemoteWrapper<List<StudentInLesson>> = doNetworkRequestForList {
        lessonService.getLessonStudents(userId)
    }

    override fun getLessonById(lessonId: Int): RemoteWrapper<Lesson> = doNetworkRequestWithMapping {
        lessonService.getLessonById(lessonId)
    }

    override fun deleteLesson(lessonId: Int): RemoteWrapper<Unit> = doNetworkRequestUnit {
        lessonService.deleteLesson(lessonId)
    }

    override fun addLesson(lesson: LessonDTO): RemoteWrapper<Unit> = doNetworkRequestUnit {
        lessonService.addLesson(lesson)
    }

    override fun updateLesson(lesson: LessonDTO): RemoteWrapper<Unit> = doNetworkRequestUnit {
        lessonService.updateLesson(lesson)
    }
}