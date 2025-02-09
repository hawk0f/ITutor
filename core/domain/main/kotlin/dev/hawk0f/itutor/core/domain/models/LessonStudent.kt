package dev.hawk0f.itutor.core.domain.models

import java.time.LocalDate
import java.time.LocalTime

class LessonStudent(
    val studentId: Int,
    val lessonId: Int,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val studentName: String,
    val price: Float,
    val hasPaid: Boolean,
    val homework: String,
    val isHomeworkDone: Boolean
)