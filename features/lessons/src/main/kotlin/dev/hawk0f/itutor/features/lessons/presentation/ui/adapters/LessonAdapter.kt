package dev.hawk0f.itutor.features.lessons.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hawk0f.itutor.core.presentation.base.BaseDiffUtilItemCallback
import dev.hawk0f.itutor.core.presentation.models.LessonUI
import dev.hawk0f.itutor.features.lessons.databinding.LessonItemBinding

class LessonAdapter(private val onLessonClick: (lessonId: Int) -> Unit, private val onDeleteClick: (lessonId: Int) -> Unit) : ListAdapter<LessonUI, LessonAdapter.LessonViewHolder>(BaseDiffUtilItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder = LessonViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int)
    {
        getItem(position)?.let { holder.bind(it, onLessonClick, onDeleteClick) }
    }

    class LessonViewHolder(private val binding: LessonItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(lessonUi: LessonUI, onLessonClick: (lessonId: Int) -> Unit, onDeleteClick: (lessonId: Int) -> Unit) = with(binding) {
            lesson = lessonUi
            root.setOnClickListener {
                onLessonClick(lessonUi.id)
            }
            deleteBtn.setOnClickListener {
                onDeleteClick(lessonUi.id)
            }
        }

        companion object
        {
            fun inflateFrom(parent: ViewGroup): LessonViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LessonItemBinding.inflate(layoutInflater, parent, false)
                return LessonViewHolder(binding)
            }
        }
    }
}