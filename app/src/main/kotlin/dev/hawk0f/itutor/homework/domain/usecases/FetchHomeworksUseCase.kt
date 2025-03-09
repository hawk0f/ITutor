package dev.hawk0f.itutor.homework.domain.usecases

import dev.hawk0f.itutor.homework.domain.repositories.HomeworkRepository
import javax.inject.Inject

class FetchHomeworksUseCase @Inject constructor(private val homeworkRepository: HomeworkRepository)
{
    operator fun invoke(userId: Int) = homeworkRepository.fetchLessonStudents(userId)
}