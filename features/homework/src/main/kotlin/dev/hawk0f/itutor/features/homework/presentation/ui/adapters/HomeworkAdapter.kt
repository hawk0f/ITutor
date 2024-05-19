package dev.hawk0f.itutor.features.homework.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hawk0f.itutor.core.presentation.base.BaseDiffUtilItemCallback
import dev.hawk0f.itutor.core.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.features.homework.databinding.HomeworkItemBinding

class HomeworkAdapter(private val onHomeworkClick: (studentId: Int, lessonId: Int, homework: String) -> Unit, private val onUpdateClick: (studentId: Int, lessonId: Int, isHomeworkDone: Boolean) -> Unit) : ListAdapter<LessonStudentUI, HomeworkAdapter.HomeworkItemViewHolder>(BaseDiffUtilItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeworkItemViewHolder = HomeworkItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: HomeworkItemViewHolder, position: Int)
    {
        getItem(position)?.let { holder.bind(it, onHomeworkClick, onUpdateClick) }
    }

    class HomeworkItemViewHolder(private val binding: HomeworkItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(lessonStudentUI: LessonStudentUI, onHomeworkClick: (studentId: Int, lessonId: Int, homework: String) -> Unit, onUpdateClick: (studentId: Int, lessonId: Int, hasPaid: Boolean) -> Unit) = with(binding) {
            lessonStudent = lessonStudentUI
            root.setOnClickListener {
                onHomeworkClick(lessonStudentUI.studentId, lessonStudentUI.lessonId, lessonStudentUI.fullHomework)
            }
            customSwitch.setOnClickListener {
                onUpdateClick(lessonStudentUI.studentId, lessonStudentUI.lessonId, customSwitch.isChecked)
            }
        }

        companion object
        {
            fun inflateFrom(parent: ViewGroup): HomeworkItemViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HomeworkItemBinding.inflate(layoutInflater, parent, false)
                return HomeworkItemViewHolder(binding)
            }
        }
    }
}