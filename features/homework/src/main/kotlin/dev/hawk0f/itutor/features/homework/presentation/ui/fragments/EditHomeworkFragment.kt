package dev.hawk0f.itutor.features.homework.presentation.ui.fragments

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.fullText
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.extensions.validateInputs
import dev.hawk0f.itutor.features.homework.R
import dev.hawk0f.itutor.features.homework.databinding.FragmentEditHomeworkBinding
import dev.hawk0f.itutor.features.homework.presentation.ui.viewmodels.EditHomeworkViewModel
import dev.hawk0f.itutor.navigation.EditHomeworkFragmentArgs

@AndroidEntryPoint
class EditHomeworkFragment : BaseFragment<EditHomeworkViewModel, FragmentEditHomeworkBinding>(R.layout.fragment_edit_homework)
{
    override val viewModel: EditHomeworkViewModel by viewModels()
    override val binding: FragmentEditHomeworkBinding by viewBinding(FragmentEditHomeworkBinding::bind)

    override fun initialize()
    {
        setupFields()
    }

    private fun setupFields()
    {
        val args = EditHomeworkFragmentArgs.fromBundle(requireArguments())
        viewModel.setLessonId(args.lessonId)
        viewModel.setStudentId(args.studentId)
        viewModel.homework = args.homework
    }

    private fun setupViewModel() = with(binding) {
        viewmodel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun setupRequests()
    {
        fetchHomeworks()
    }

    private fun fetchHomeworks()
    {
        viewModel.fetchHomeworks()
    }

    override fun setupSubscribers()
    {
        subscribeToHomeworks()
        subscribeToUpdate()
    }

    private fun subscribeToHomeworks() = with(binding) {
        viewModel.homeworksState.collectAsUIState(state = {
            it.setupViewVisibilityCircular(group, loader)
        }, onSuccess = {
            val indexOfCurrentLesson =
                it.indexOfFirst { lessonStudent -> lessonStudent.lessonId == viewModel.getLessonId() }
            lessonEditText.setText("Дата: " + it[indexOfCurrentLesson].date.parseToFormat("d MMMM") + "\nВремя: " + it[indexOfCurrentLesson].startTime + " - " + it[indexOfCurrentLesson].endTime)

            val currentStudent =
                it.first { lessonStudent -> lessonStudent.studentId == viewModel.getStudentId() }
            studentEditText.setText(currentStudent.studentName)

            setupViewModel()
        })
    }

    private fun subscribeToUpdate() = with(binding) {
        viewModel.updateState.collectAsUIState(state = {
            it.setupViewVisibilityCircular(group, loader, false)
        }, onSuccess = {
            findNavController().popBackStack()
        })
    }

    override fun setupListeners()
    {
        setupUpdateHomeworkButtonListener()
    }

    private fun setupUpdateHomeworkButtonListener() = with(binding) {
        btnUpdateHomework.setOnClickListener {
            if (homeworkEditText.fullText.isNotEmpty())
            {
                viewModel.updateHomework()
            }
            else
            {
                showToastLong("Введите домашнее задание")
            }
        }
    }
}