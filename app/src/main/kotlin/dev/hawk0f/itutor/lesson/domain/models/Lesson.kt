package dev.hawk0f.itutor.lesson.domain.models

import java.time.LocalDate
import java.time.LocalTime

data class Lesson(
    val id: Int,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val studentsIds: MutableList<Int>,
    val students: MutableList<StudentInLesson>,
    val subject: Subject,
    val userId: Int
)