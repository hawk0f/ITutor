package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.domain.models.Lesson
import dev.hawk0f.itutor.core.domain.models.LessonStudent
import dev.hawk0f.itutor.core.domain.models.Student
import dev.hawk0f.itutor.core.domain.models.Subject
import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class LessonUI(
    override val id: Int,
    val date: String,
    val startTime: String,
    val endTime: String,
    val students: MutableList<LessonStudent>,
    val studentsNames: String,
    val subject: Subject,
    val userId: Int) : IBaseDiffModel<Int>

private val dtf = DateTimeFormatter.ofPattern("dd MMMM, EEEE")
private val tdf = DateTimeFormatter.ofPattern("HH:mm")

fun Lesson.toUi() = LessonUI(id, date.format(dtf), startTime.format(tdf), endTime.format(tdf), students, students.joinToString(limit = 2) { it.name }, subject, userId)