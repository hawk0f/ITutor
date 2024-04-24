package dev.hawk0f.itutor.features.students.presentation.ui.fragments

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.features.students.R
import dev.hawk0f.itutor.features.students.databinding.FragmentStudentsBinding
import dev.hawk0f.itutor.features.students.presentation.ui.adapter.StudentAdapter
import dev.hawk0f.itutor.features.students.presentation.ui.viewmodels.StudentsViewModel
import androidx.navigation.fragment.findNavController
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.navigation.R.id.action_studentsFragment_to_addStudentFragment
import dev.hawk0f.itutor.navigation.R.id.action_studentsFragment_to_editStudentFragment

@AndroidEntryPoint
class StudentFragment : BaseFragment<StudentsViewModel, FragmentStudentsBinding>(R.layout.fragment_students)
{
    override val viewModel: StudentsViewModel by viewModels()
    override val binding: FragmentStudentsBinding by viewBinding(FragmentStudentsBinding::bind)

    private val studentAdapter = StudentAdapter({
        findNavController().navigateSafely(object : NavDirections
        {
            override val actionId: Int = action_studentsFragment_to_editStudentFragment
            override val arguments: Bundle = bundleOf("studentId" to it)
        })
    }, {
        viewModel.deleteStudent(it)
    })

    override fun initialize()
    {
        setupRecycler()
    }

    private fun setupRecycler() = with(binding) {
        with(recyclerStudents) {
            layoutManager = LinearLayoutManager(context)
            adapter = studentAdapter
        }
    }

    override fun setupListeners() = with(binding) {
        btnAddStudent.setOnClickListener {
            findNavController().navigateSafely(action_studentsFragment_to_addStudentFragment)
        }
    }

    override fun setupRequests()
    {
        fetchStudents()
    }

    private fun fetchStudents()
    {
        viewModel.fetchStudents()
    }

    override fun setupSubscribers()
    {
        subscribeToStudents()
        subscribeToDelete()
    }

    private fun subscribeToStudents() = with(binding) {
        viewModel.studentState.collectAsUIState(state = {
            it.setupViewVisibility(group, loader)
        }, onSuccess = {
            studentAdapter.submitList(it)
        })
    }

    private fun subscribeToDelete() = with(binding) {
        viewModel.deleteState.collectAsUIState(state = {
            it.setupViewVisibility(group, loader)
        }, onSuccess = {
            fetchStudents()
        })
    }
}