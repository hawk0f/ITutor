package dev.hawk0f.itutor.features.lessons.domain.usecases

import dev.hawk0f.itutor.features.lessons.domain.repositories.LessonRepository
import javax.inject.Inject

class DeleteLessonUseCase @Inject constructor(private val repository: LessonRepository)
{
    operator fun invoke(lessonId: Int) = repository.deleteLesson(lessonId)
}