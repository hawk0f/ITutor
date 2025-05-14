package dev.hawk0f.itutor.finance.presentation.models

import dev.hawk0f.itutor.core.data.utils.DateSerializer
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.core.presentation.extensions.takeDot
import dev.hawk0f.itutor.finance.domain.models.LessonStudent
import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class LessonStudentUI(
    val id: Int,
    val studentId: Int,
    val lessonId: Int,
    val parsedDate: String,
    @Serializable(with = DateSerializer::class)
    val date: LocalDate,
    val startTime: String,
    val endTime: String,
    val studentName: String,
    val price: Float,
    val hasPaid: Boolean,
    val fullHomework: String,
    val isHomeworkDone: Boolean,
    val shortHomework: String = fullHomework.takeDot(10)
)

fun LessonStudent.toUi() = LessonStudentUI(
        "${studentId}${lessonId}".toInt(),
        studentId,
        lessonId,
        date.parseToFormat("dd MMMM, EEEE"),
        date,
        startTime.parseToFormat("HH:mm"),
        endTime.parseToFormat("HH:mm"),
        studentName,
        price,
        hasPaid,
        homework,
        isHomeworkDone
)