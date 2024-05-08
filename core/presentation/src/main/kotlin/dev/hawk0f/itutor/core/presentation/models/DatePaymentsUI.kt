package dev.hawk0f.itutor.core.presentation.models

import dev.hawk0f.itutor.core.presentation.base.IBaseDiffModel

data class DatePaymentsUI(
    val date: String,
    override val id: String = date,
    val payments: MutableList<PaymentUI>
) : IBaseDiffModel<String>