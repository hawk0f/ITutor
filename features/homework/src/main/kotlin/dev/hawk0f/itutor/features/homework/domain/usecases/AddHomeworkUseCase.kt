package dev.hawk0f.itutor.features.homework.domain.usecases

import dev.hawk0f.itutor.features.homework.domain.repositories.HomeworkRepository
import javax.inject.Inject

class AddHomeworkUseCase @Inject constructor(private val homeworkRepository: HomeworkRepository)
{
    operator fun invoke(studentId: Int, lessonId: Int, homework: String) = homeworkRepository.addHomework(studentId, lessonId, homework)
}