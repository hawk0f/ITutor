package dev.hawk0f.itutor.lesson.domain.usecases

import dev.hawk0f.itutor.lesson.domain.repositories.LessonRepository
import javax.inject.Inject

class GetLessonByIdUseCase @Inject constructor(private val repository: LessonRepository)
{
    operator fun invoke(lessonId: Int) = repository.getLessonById(lessonId)
}