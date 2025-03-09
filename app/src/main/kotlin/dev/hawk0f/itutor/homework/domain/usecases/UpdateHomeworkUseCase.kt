package dev.hawk0f.itutor.homework.domain.usecases

import dev.hawk0f.itutor.homework.domain.repositories.HomeworkRepository
import javax.inject.Inject

class UpdateHomeworkUseCase @Inject constructor(private val homeworkRepository: HomeworkRepository)
{
    operator fun invoke(studentId: Int, lessonId: Int, homework: String) = homeworkRepository.updateHomework(studentId, lessonId, homework)
}