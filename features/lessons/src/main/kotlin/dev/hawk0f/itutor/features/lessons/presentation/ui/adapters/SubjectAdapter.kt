package dev.hawk0f.itutor.features.lessons.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hawk0f.itutor.core.presentation.base.BaseDiffUtilItemCallback
import dev.hawk0f.itutor.core.presentation.models.SubjectUI
import dev.hawk0f.itutor.features.lessons.databinding.SubjectItemBinding

class SubjectAdapter(private val onSubjectClick: (subjectId: Int) -> Unit) : ListAdapter<SubjectUI, SubjectAdapter.SubjectViewHolder>(BaseDiffUtilItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder = SubjectViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int)
    {
        getItem(position)?.let { holder.bind(it, onSubjectClick) }
    }

    class SubjectViewHolder(private val binding: SubjectItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(subjectUi: SubjectUI, onSubjectClick: (lessonId: Int) -> Unit) = with(binding) {
            subject = subjectUi
            root.setOnClickListener {
                onSubjectClick(subjectUi.id)
            }
        }

        companion object
        {
            fun inflateFrom(parent: ViewGroup): SubjectViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SubjectItemBinding.inflate(layoutInflater, parent, false)
                return SubjectViewHolder(binding)
            }
        }
    }
}