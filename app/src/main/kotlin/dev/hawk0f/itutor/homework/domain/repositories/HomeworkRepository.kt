package dev.hawk0f.itutor.homework.domain.repositories

import dev.hawk0f.itutor.core.domain.RemoteWrapper
import dev.hawk0f.itutor.finance.domain.models.LessonStudent

interface HomeworkRepository
{
    fun fetchLessonStudents(userId: Int): RemoteWrapper<List<LessonStudent>>

    fun addHomework(studentId: Int, lessonId: Int, homework: String): RemoteWrapper<Unit>

    fun updateHomework(studentId: Int, lessonId: Int, homework: String) : RemoteWrapper<Unit>

    fun updateHomeworkStatus(studentId: Int, lessonId: Int, isHomeworkDone: Boolean): RemoteWrapper<Unit>
}