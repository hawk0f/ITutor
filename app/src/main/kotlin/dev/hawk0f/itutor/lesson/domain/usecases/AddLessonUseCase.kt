package dev.hawk0f.itutor.lesson.domain.usecases

import dev.hawk0f.itutor.lesson.data.models.LessonDTO
import dev.hawk0f.itutor.lesson.domain.repositories.LessonRepository
import javax.inject.Inject

class AddLessonUseCase @Inject constructor(private val repository: LessonRepository)
{
    operator fun invoke(lesson: LessonDTO) = repository.addLesson(lesson)
}