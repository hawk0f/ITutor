package dev.hawk0f.itutor.features.students.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.features.students.R
import dev.hawk0f.itutor.features.students.databinding.FragmentEditStudentBinding
import dev.hawk0f.itutor.features.students.presentation.ui.viewmodels.EditStudentViewModel

private const val ARG_PARAM1 = "studentId"

@AndroidEntryPoint
class EditStudentFragment : BaseFragment<EditStudentViewModel, FragmentEditStudentBinding>(R.layout.fragment_edit_student)
{
    override val viewModel: EditStudentViewModel by viewModels()
    override val binding: FragmentEditStudentBinding by viewBinding(FragmentEditStudentBinding::bind)

    private var studentId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
            studentId = it.getInt(ARG_PARAM1)
        }
    }

    override fun initialize()
    {
        setupFields()
    }

    private fun setupFields()
    {
        viewModel.clearFields()
    }

    private fun setupViewModel() = with(binding) {
        viewmodel = viewModel
        lifecycleOwner = this@EditStudentFragment.viewLifecycleOwner
    }

    override fun setupRequests()
    {
        fetchStudent()
    }

    private fun fetchStudent()
    {
        viewModel.getStudentById(studentId)
    }

    override fun setupSubscribers()
    {
        subscribeToStudent()
        subscribeToUpdate()
    }

    private fun subscribeToStudent() = with(binding) {
        viewModel.studentState.collectAsUIState(state = {
            it.setupViewVisibility(group, loader)
        }, onSuccess = {
            viewModel.setData(it)
            setupViewModel()
        })
    }

    private fun subscribeToUpdate() = with(binding) {
        viewModel.updateState.collectAsUIState(state = {
            it.setupViewVisibility(group, loader)
        }, onSuccess = {
            showToastLong("Успешно обновлено")
            findNavController().popBackStack()
        })
    }
}