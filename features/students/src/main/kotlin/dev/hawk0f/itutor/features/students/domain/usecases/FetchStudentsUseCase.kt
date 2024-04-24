package dev.hawk0f.itutor.features.students.domain.usecases

import dev.hawk0f.itutor.features.students.domain.repositories.StudentsRepository
import javax.inject.Inject

class FetchStudentsUseCase @Inject constructor(private val repository: StudentsRepository)
{
    operator fun invoke(userId: Int) = repository.fetchStudents(userId)
}
