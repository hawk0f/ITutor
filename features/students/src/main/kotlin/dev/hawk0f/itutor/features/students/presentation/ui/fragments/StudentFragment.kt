package dev.hawk0f.itutor.features.students.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.base.BaseHorizontalDividerItemDecoration
import dev.hawk0f.itutor.core.presentation.base.BaseVerticalDividerItemDecoration
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.features.students.R
import dev.hawk0f.itutor.features.students.databinding.FragmentStudentBinding
import dev.hawk0f.itutor.features.students.presentation.ui.adapters.StudentAdapter
import dev.hawk0f.itutor.features.students.presentation.ui.viewmodels.StudentsViewModel
import dev.hawk0f.itutor.navigation.StudentFragmentDirections
import jp.wasabeef.recyclerview.animators.FadeInAnimator

@AndroidEntryPoint
class StudentFragment : BaseFragment<StudentsViewModel, FragmentStudentBinding>(R.layout.fragment_student)
{
    override val viewModel: StudentsViewModel by viewModels()
    override val binding: FragmentStudentBinding by viewBinding(FragmentStudentBinding::bind)

    private val studentAdapter = StudentAdapter({
        findNavController().navigateSafely(StudentFragmentDirections.actionStudentsFragmentToEditStudentFragment(it))
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

            itemAnimator = FadeInAnimator()

            addItemDecoration(BaseHorizontalDividerItemDecoration(40))
            addItemDecoration(BaseVerticalDividerItemDecoration(30, 10))
        }
    }

    override fun setupListeners()
    {
        setupAddStudentButton()
    }

    private fun setupAddStudentButton() = with(binding) {
        btnAddStudent.setOnClickListener {
            findNavController().navigateSafely(StudentFragmentDirections.actionStudentsFragmentToAddStudentFragment())
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
            it.setupViewVisibilityLinear(group, loader)
        }, onSuccess = {
            studentAdapter.submitList(it)
        })
    }

    private fun subscribeToDelete() = with(binding) {
        viewModel.deleteState.collectAsUIState(state = {
            it.setupViewVisibilityLinear(group, loader, false)
        }, onSuccess = {
            fetchStudents()
        })
    }
}