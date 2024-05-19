package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.domain.models.LessonStudent
import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.core.presentation.extensions.takeDot
import java.io.Serializable
import java.time.LocalDate

data class LessonStudentUI(
    override val id: Int,
    val studentId: Int,
    val lessonId: Int,
    val parsedDate: String,
    val date: LocalDate,
    val startTime: String,
    val endTime: String,
    val studentName: String,
    val price: Float,
    val hasPaid: Boolean,
    val fullHomework: String,
    val isHomeworkDone: Boolean,
    val shortHomework: String = fullHomework.takeDot(5)
) : IBaseDiffModel<Int>, Serializable

fun LessonStudent.toUi() = LessonStudentUI("${studentId}${lessonId}".toInt(), studentId, lessonId, date.parseToFormat("dd MMMM, EEEE"), date, startTime.parseToFormat("HH:mm"), endTime.parseToFormat("HH:mm"), studentName, price, hasPaid, homework, isHomeworkDone)