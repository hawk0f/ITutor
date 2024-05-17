package dev.hawk0f.itutor.core.data.models

import dev.hawk0f.itutor.core.data.utils.DataMapper
import dev.hawk0f.itutor.core.data.utils.DateSerializer
import dev.hawk0f.itutor.core.data.utils.TimeSerializer
import dev.hawk0f.itutor.core.domain.models.Lesson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Serializable
class LessonDTO(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("date")
    @Serializable(DateSerializer::class)
    val date: LocalDate,
    @SerialName("startTime")
    @Serializable(TimeSerializer::class)
    val startTime: LocalTime,
    @SerialName("durationInMinutes")
    val durationInMinutes: Long,
    @SerialName("studentsIds")
    val studentsIds: MutableList<Int>,
    @SerialName("students")
    val students: MutableList<StudentInLessonDTO>? = null,
    @SerialName("subjectId")
    val subjectId: Int,
    @SerialName("subject")
    val subject: SubjectDTO? = null,
    @SerialName("userId")
    val userId: Int
) : DataMapper<Lesson>
{
    override fun toDomain(): Lesson = Lesson(id, date, startTime, startTime.plusMinutes(durationInMinutes), studentsIds, students!!.map { it.toDomain() }.toMutableList(), subject!!.toDomain(), userId)
}