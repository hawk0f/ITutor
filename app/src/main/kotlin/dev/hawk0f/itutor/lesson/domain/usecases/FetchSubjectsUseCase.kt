package dev.hawk0f.itutor.lesson.domain.usecases

import dev.hawk0f.itutor.lesson.domain.repositories.SubjectRepository
import javax.inject.Inject

class FetchSubjectsUseCase @Inject constructor(private val repository: SubjectRepository)
{
    operator fun invoke() = repository.fetchSubjects()
}