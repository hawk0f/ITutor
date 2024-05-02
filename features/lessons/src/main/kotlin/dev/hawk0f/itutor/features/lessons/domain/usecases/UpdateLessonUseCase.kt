package dev.hawk0f.itutor.features.lessons.domain.usecases

import dev.hawk0f.itutor.core.data.models.LessonDTO
import dev.hawk0f.itutor.features.lessons.domain.repositories.LessonRepository
import javax.inject.Inject

class UpdateLessonUseCase @Inject constructor(private val repository: LessonRepository)
{
    operator fun invoke(lesson: LessonDTO) = repository.updateLesson(lesson)
}