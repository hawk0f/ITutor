package dev.hawk0f.itutor.features.lessons.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hawk0f.itutor.core.presentation.base.BaseDiffUtilItemCallback
import dev.hawk0f.itutor.core.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.features.lessons.databinding.StudentItemBinding
import dev.hawk0f.itutor.features.lessons.presentation.ui.adapters.LessonStudentsAdapter.LessonStudentsViewHolder

class LessonStudentsAdapter(private val onDeleteClick: (studentId: Int) -> Unit) : ListAdapter<LessonStudentUI, LessonStudentsViewHolder>(BaseDiffUtilItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonStudentsViewHolder = LessonStudentsViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: LessonStudentsViewHolder, position: Int)
    {
        getItem(position)?.let { holder.bind(it, onDeleteClick) }
    }

    class LessonStudentsViewHolder(private val binding: StudentItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(studentUi: LessonStudentUI, onDeleteClick: (studentId: Int) -> Unit) = with(binding) {
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
                val binding = StudentItemBinding.inflate(layoutInflater, parent, false)
                return LessonStudentsViewHolder(binding)
            }
        }
    }
}