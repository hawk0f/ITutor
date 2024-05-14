package dev.hawk0f.itutor.features.lessons.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hawk0f.itutor.core.presentation.base.BaseDiffUtilItemCallback
import dev.hawk0f.itutor.core.presentation.models.DateLessonsUI
import dev.hawk0f.itutor.features.lessons.databinding.DateLessonsItemBinding
import javax.inject.Inject

class DateLessonsAdapter(private val onLessonClick: (lessonId: Int) -> Unit, private val onDeleteClick: (lessonId: Int) -> Unit) : ListAdapter<DateLessonsUI, DateLessonsAdapter.DateLessonsViewHolder>(BaseDiffUtilItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateLessonsViewHolder = DateLessonsViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: DateLessonsViewHolder, position: Int)
    {
        getItem(position)?.let { holder.bind(it, onLessonClick, onDeleteClick) }
    }

    class DateLessonsViewHolder(private val binding: DateLessonsItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(dateLessonsUI: DateLessonsUI, onLessonClick: (lessonId: Int) -> Unit, onDeleteClick: (lessonId: Int) -> Unit) = with(binding) {
            date.text = dateLessonsUI.date
            val lessonAdapter = LessonAdapter(onLessonClick, onDeleteClick)
            lessonAdapter.submitList(dateLessonsUI.lessons)
            recyclerLessons.adapter = lessonAdapter
        }

        companion object
        {
            fun inflateFrom(parent: ViewGroup): DateLessonsViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DateLessonsItemBinding.inflate(layoutInflater, parent, false)
                return DateLessonsViewHolder(binding)
            }
        }
    }
}