package dev.hawk0f.itutor.features.lessons.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hawk0f.itutor.core.presentation.base.BaseDiffUtilItemCallback
import dev.hawk0f.itutor.core.presentation.models.StudentInLessonUI
import dev.hawk0f.itutor.features.lessons.databinding.ChooseLessonStudentItemBinding

class ChooseStudentAdapter(private val onCheckBoxClick: (studentId: Int) -> Unit) : ListAdapter<StudentInLessonUI, ChooseStudentAdapter.ChooseStudentViewHolder>(BaseDiffUtilItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseStudentViewHolder = ChooseStudentViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ChooseStudentViewHolder, position: Int)
    {
        getItem(position)?.let { holder.bind(it, onCheckBoxClick) }
    }

    class ChooseStudentViewHolder(private val binding: ChooseLessonStudentItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(studentUi: StudentInLessonUI, onCheckBoxClick: (studentId: Int) -> Unit) = with(binding) {
            student = studentUi
            checkbox.setOnClickListener {
                onCheckBoxClick(studentUi.id)
            }
        }

        companion object
        {
            fun inflateFrom(parent: ViewGroup): ChooseStudentViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChooseLessonStudentItemBinding.inflate(layoutInflater, parent, false)
                return ChooseStudentViewHolder(binding)
            }
        }
    }
}