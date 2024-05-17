package dev.hawk0f.itutor.features.homework.domain.usecases

import dev.hawk0f.itutor.features.homework.domain.repositories.HomeworkRepository
import javax.inject.Inject

class FetchHomeworksUseCase @Inject constructor(private val homeworkRepository: HomeworkRepository)
{
    operator fun invoke(userId: Int) = homeworkRepository.fetchLessonStudents(userId)
}