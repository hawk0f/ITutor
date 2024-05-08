package dev.hawk0f.itutor.features.finance.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hawk0f.itutor.core.presentation.base.BaseDiffUtilItemCallback
import dev.hawk0f.itutor.core.presentation.models.DatePaymentsUI
import dev.hawk0f.itutor.features.finance.databinding.DatePaymentsItemBinding
import dev.hawk0f.itutor.features.finance.presentation.ui.adapters.DatePaymentsAdapter.DatePaymentsViewHolder

class DatePaymentsAdapter(private val onUpdateClick: (studentId: Int, lessonId: Int, hasPaid: Boolean) -> Unit) : ListAdapter<DatePaymentsUI, DatePaymentsViewHolder>(BaseDiffUtilItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatePaymentsViewHolder = DatePaymentsViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: DatePaymentsViewHolder, position: Int)
    {
        getItem(position)?.let { holder.bind(it, onUpdateClick) }
    }

    class DatePaymentsViewHolder(private val binding: DatePaymentsItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(datePayments: DatePaymentsUI, onUpdateClick: (studentId: Int, lessonId: Int, hasPaid: Boolean) -> Unit) = with(binding) {
            date.text = datePayments.date
            val lessonAdapter = PaymentAdapter(onUpdateClick)
            lessonAdapter.submitList(datePayments.payments)
            recyclerPayments.adapter = lessonAdapter
        }

        companion object
        {
            fun inflateFrom(parent: ViewGroup): DatePaymentsViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DatePaymentsItemBinding.inflate(layoutInflater, parent, false)
                return DatePaymentsViewHolder(binding)
            }
        }
    }
}