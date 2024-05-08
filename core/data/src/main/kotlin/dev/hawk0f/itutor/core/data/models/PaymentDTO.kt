package dev.hawk0f.itutor.core.data.models

import dev.hawk0f.itutor.core.data.utils.DataMapper
import dev.hawk0f.itutor.core.data.utils.DateSerializer
import dev.hawk0f.itutor.core.data.utils.TimeSerializer
import dev.hawk0f.itutor.core.domain.models.Payment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Serializable
class PaymentDTO(
    @SerialName("studentId")
    val studentId: Int,
    @SerialName("lessonId")
    val lessonId: Int,
    @SerialName("date")
    @Serializable(DateSerializer::class)
    val date: LocalDate,
    @SerialName("startTime")
    @Serializable(TimeSerializer::class)
    val startTime: LocalTime,
    @SerialName("durationInMinutes")
    val durationInMinutes: Long,
    @SerialName("studentName")
    val studentName: String,
    @SerialName("price")
    val price: Float,
    @SerialName("hasPaid")
    val hasPaid: Boolean
) : DataMapper<Payment>
{
    override fun toDomain(): Payment = Payment(studentId, lessonId, date, startTime, startTime.plusMinutes(durationInMinutes), studentName, price, hasPaid)
}