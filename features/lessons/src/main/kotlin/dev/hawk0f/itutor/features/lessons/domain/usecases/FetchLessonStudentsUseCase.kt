package dev.hawk0f.itutor.features.lessons.domain.usecases

import dev.hawk0f.itutor.features.lessons.domain.repositories.LessonRepository
import javax.inject.Inject

class FetchLessonStudentsUseCase @Inject constructor(private val repository: LessonRepository)
{
    operator fun invoke(userId: Int, studentsCount: Int) = repository.fetchLessonStudents(userId, studentsCount)
}