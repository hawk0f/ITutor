package dev.hawk0f.itutor.core.domain.models

import java.time.LocalDate
import java.time.LocalTime

class Lesson(
    val id: Int,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val studentsIds: List<Int>,
    val students: MutableList<LessonStudent>,
    val subjectId: Int,
    var subject: Subject,
    val userId: Int
)