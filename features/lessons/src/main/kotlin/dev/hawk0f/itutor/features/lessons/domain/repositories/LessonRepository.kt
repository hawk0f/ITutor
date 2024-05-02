package dev.hawk0f.itutor.features.lessons.domain.repositories

import dev.hawk0f.itutor.core.data.models.LessonDTO
import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.core.domain.models.Lesson
import dev.hawk0f.itutor.core.domain.models.Student

interface LessonRepository
{
    fun fetchLessons(userId: Int): RemoteWrapper<List<Lesson>>

    fun getLessonById(lessonId: Int): RemoteWrapper<Lesson>

    fun deleteLesson(lessonId: Int): RemoteWrapper<Unit>

    fun addLesson(lesson: LessonDTO): RemoteWrapper<Unit>

    fun updateLesson(lesson: LessonDTO): RemoteWrapper<Unit>
}