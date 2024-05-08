package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.domain.models.Payment
import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import java.time.LocalDate

data class PaymentUI(
    override val id: Int,
    val studentId: Int,
    val lessonId: Int,
    val parsedDate: String,
    val date: LocalDate,
    val startTime: String,
    val endTime: String,
    val studentName: String,
    val price: Float,
    val hasPaid: Boolean
) : IBaseDiffModel<Int>

fun Payment.toUi() = PaymentUI("${studentId}${lessonId}".toInt(), studentId, lessonId, date.parseToFormat("dd MMMM, EEEE"), date, startTime.parseToFormat("HH:mm"), endTime.parseToFormat("HH:mm"), studentName, price, hasPaid)
