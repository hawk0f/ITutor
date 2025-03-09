package dev.hawk0f.itutor.lesson.presentation.models

import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.lesson.domain.models.Subject
import java.io.Serializable
import java.time.LocalDate

data class LessonUI(
    override val id: Int,
    val parsedDate: String,
    val date: LocalDate,
    val startTime: String,
    val endTime: String,
    val studentsIds: MutableList<Int>,
    val studentsNames: String,
    val subject: Subject,
    val userId: Int) : IBaseDiffModel<Int>, Serializable

fun dev.hawk0f.itutor.lesson.domain.models.Lesson.toUi() = LessonUI(id, date.parseToFormat("dd MMMM, EEEE"), date, startTime.parseToFormat("HH:mm"), endTime.parseToFormat("HH:mm"), studentsIds, students.joinToString(limit = 2) { it.name }, subject, userId)