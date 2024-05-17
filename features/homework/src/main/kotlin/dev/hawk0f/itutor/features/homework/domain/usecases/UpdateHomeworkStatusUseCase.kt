package dev.hawk0f.itutor.features.homework.domain.usecases

import dev.hawk0f.itutor.features.homework.domain.repositories.HomeworkRepository
import javax.inject.Inject

class UpdateHomeworkStatusUseCase @Inject constructor(private val homeworkRepository: HomeworkRepository)
{
    operator fun invoke(studentId: Int, lessonId: Int, isHomeworkDone: Boolean) = homeworkRepository.updateHomeworkStatus(studentId, lessonId, isHomeworkDone)
}