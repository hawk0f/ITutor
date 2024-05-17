package dev.hawk0f.itutor.features.lessons.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hawk0f.itutor.core.presentation.base.BaseDiffUtilItemCallback
import dev.hawk0f.itutor.core.presentation.models.StudentInLessonUI
import dev.hawk0f.itutor.features.lessons.databinding.StudentInLessonItemBinding
import dev.hawk0f.itutor.features.lessons.presentation.ui.adapters.LessonStudentsAdapter.LessonStudentsViewHolder

class LessonStudentsAdapter(private val onDeleteClick: (studentId: Int) -> Unit) : ListAdapter<StudentInLessonUI, LessonStudentsViewHolder>(BaseDiffUtilItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonStudentsViewHolder = LessonStudentsViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: LessonStudentsViewHolder, position: Int)
    {
        getItem(position)?.let { holder.bind(it, onDeleteClick) }
    }

    class LessonStudentsViewHolder(private val binding: StudentInLessonItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(studentUi: StudentInLessonUI, onDeleteClick: (studentId: Int) -> Unit) = with(binding) {
            student = studentUi
            deleteBtn.setOnClickListener {
                onDeleteClick(studentUi.id)
            }
        }

        companion object
        {
            fun inflateFrom(parent: ViewGroup): LessonStudentsViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StudentInLessonItemBinding.inflate(layoutInflater, parent, false)
                return LessonStudentsViewHolder(binding)
            }
        }
    }
}