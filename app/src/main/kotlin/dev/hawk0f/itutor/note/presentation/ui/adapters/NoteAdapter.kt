package dev.hawk0f.itutor.note.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hawk0f.itutor.core.presentation.base.BaseDiffUtilItemCallback
import dev.hawk0f.itutor.databinding.NoteItemBinding
import dev.hawk0f.itutor.note.presentation.models.NoteUI

class NoteAdapter(private val onNoteClick: (studentId: Int) -> Unit, private val onDeleteClick: (studentId: Int) -> Unit) : ListAdapter<NoteUI, NoteAdapter.NoteItemViewHolder>(BaseDiffUtilItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder = NoteItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int)
    {
        getItem(position)?.let { holder.bind(it, onNoteClick, onDeleteClick) }
    }

    class NoteItemViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(noteUi: NoteUI, onStudentClick: (studentId: Int) -> Unit, onDeleteClick: (studentId: Int) -> Unit) = with(binding) {
            note = noteUi
            root.setOnClickListener {
                onStudentClick(noteUi.id)
            }
            deleteBtn.setOnClickListener {
                onDeleteClick(noteUi.id)
            }
        }

        companion object
        {
            fun inflateFrom(parent: ViewGroup): NoteItemViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteItemBinding.inflate(layoutInflater, parent, false)
                return NoteItemViewHolder(binding)
            }
        }
    }
}