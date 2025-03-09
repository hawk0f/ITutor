package dev.hawk0f.itutor.finance.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hawk0f.itutor.core.presentation.base.BaseDiffUtilItemCallback
import dev.hawk0f.itutor.databinding.PaymentItemBinding
import dev.hawk0f.itutor.finance.presentation.models.LessonStudentUI

class PaymentAdapter(private val onUpdateClick: (studentId: Int, lessonId: Int, hasPaid: Boolean) -> Unit) : ListAdapter<LessonStudentUI, PaymentAdapter.PaymentViewHolder>(BaseDiffUtilItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder = PaymentViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int)
    {
        getItem(position)?.let { holder.bind(it, onUpdateClick) }
    }

    class PaymentViewHolder(private val binding: PaymentItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(paymentUi: LessonStudentUI, onUpdateClick: (studentId: Int, lessonId: Int, hasPaid: Boolean) -> Unit) = with(binding) {
            payment = paymentUi
            customSwitch.setOnClickListener {
                onUpdateClick(paymentUi.studentId, paymentUi.lessonId, customSwitch.isChecked)
            }
//            customSwitch.setOnCheckedChangeListener { _, isChecked ->
//                onUpdateClick(paymentUi.studentId, paymentUi.lessonId, isChecked)
//            }
        }

        companion object
        {
            fun inflateFrom(parent: ViewGroup): PaymentViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PaymentItemBinding.inflate(layoutInflater, parent, false)
                return PaymentViewHolder(binding)
            }
        }
    }
}