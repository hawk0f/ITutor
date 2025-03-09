package dev.hawk0f.itutor.lesson.domain.usecases

import dev.hawk0f.itutor.lesson.domain.repositories.LessonRepository
import javax.inject.Inject

class FetchLessonStudentsUseCase @Inject constructor(private val repository: LessonRepository)
{
    operator fun invoke(userId: Int) = repository.fetchLessonStudents(userId)
}