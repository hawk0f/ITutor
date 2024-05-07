package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.domain.models.Lesson
import dev.hawk0f.itutor.core.domain.models.Subject
import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

private val dtf = DateTimeFormatter.ofPattern("dd MMMM, EEEE")
private val tdf = DateTimeFormatter.ofPattern("HH:mm")

fun Lesson.toUi() = LessonUI(id, date.format(dtf), date, startTime.format(tdf), endTime.format(tdf), studentsIds, students.joinToString(limit = 2) { it.name }, subject, userId)