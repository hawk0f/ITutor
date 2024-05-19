package dev.hawk0f.itutor.features.homework.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hawk0f.itutor.core.presentation.base.BaseDiffUtilItemCallback
import dev.hawk0f.itutor.core.presentation.models.StudentHomeworksUI
import dev.hawk0f.itutor.features.homework.databinding.StudentHomeworksItemBinding

class StudentHomeworksAdapter(private val onHomeworkClick: (studentId: Int, lessonId: Int, homework: String) -> Unit, private val onUpdateClick: (studentId: Int, lessonId: Int, isHomeworkDone: Boolean) -> Unit) : ListAdapter<StudentHomeworksUI, StudentHomeworksAdapter.StudentHomeworksViewHolder>(BaseDiffUtilItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHomeworksViewHolder = StudentHomeworksViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: StudentHomeworksViewHolder, position: Int)
    {
        getItem(position)?.let { holder.bind(it, onHomeworkClick, onUpdateClick) }
    }

    class StudentHomeworksViewHolder(private val binding: StudentHomeworksItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(studentHomeworks: StudentHomeworksUI, onHomeworkClick: (studentId: Int, lessonId: Int, homework: String) -> Unit, onUpdateClick: (studentId: Int, lessonId: Int, isHomeworkDone: Boolean) -> Unit) = with(binding) {
            studentName.text = studentHomeworks.studentName
            val homeworkAdapter = HomeworkAdapter(onHomeworkClick, onUpdateClick)
            homeworkAdapter.submitList(studentHomeworks.homeworks)
            recyclerHomeworks.adapter = homeworkAdapter
        }

        companion object
        {
            fun inflateFrom(parent: ViewGroup): StudentHomeworksViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StudentHomeworksItemBinding.inflate(layoutInflater, parent, false)
                return StudentHomeworksViewHolder(binding)
            }
        }
    }
}