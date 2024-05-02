package dev.hawk0f.itutor.features.lessons.data.repositories

import dev.hawk0f.itutor.core.data.apiservices.LessonService
import dev.hawk0f.itutor.core.data.apiservices.StudentService
import dev.hawk0f.itutor.core.data.base.BaseRepository
import dev.hawk0f.itutor.core.data.models.LessonDTO
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.Lesson
import dev.hawk0f.itutor.core.domain.models.Student
import dev.hawk0f.itutor.features.lessons.domain.repositories.LessonRepository
import javax.inject.Inject

class LessonRepositoryImpl @Inject constructor(private val lessonService: LessonService) : BaseRepository(), LessonRepository
{
    override fun fetchLessons(userId: Int): RemoteWrapper<List<Lesson>> = doNetworkRequestForList {
        lessonService.fetchLessons(userId)
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