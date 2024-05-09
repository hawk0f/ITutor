package dev.hawk0f.itutor.features.students.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hawk0f.itutor.core.presentation.base.BaseDiffUtilItemCallback
import dev.hawk0f.itutor.core.presentation.models.StudentUI
import dev.hawk0f.itutor.features.students.databinding.StudentItemBinding

class StudentAdapter(private val onStudentClick: (studentId: Int) -> Unit, private val onDeleteClick: (studentId: Int) -> Unit) : ListAdapter<StudentUI, StudentAdapter.StudentItemViewHolder>(BaseDiffUtilItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentItemViewHolder = StudentItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: StudentItemViewHolder, position: Int)
    {
        getItem(position)?.let { holder.bind(it, onStudentClick, onDeleteClick) }
    }

    class StudentItemViewHolder(private val binding: StudentItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(studentUi: StudentUI, onStudentClick: (studentId: Int) -> Unit, onDeleteClick: (studentId: Int) -> Unit) = with(binding) {
            student = studentUi
            root.setOnClickListener {
                onStudentClick(studentUi.id)
            }
            deleteBtn.setOnClickListener {
                onDeleteClick(studentUi.id)
            }
        }

        companion object
        {
            fun inflateFrom(parent: ViewGroup): StudentItemViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StudentItemBinding.inflate(layoutInflater, parent, false)
                return StudentItemViewHolder(binding)
            }
        }
    }
}