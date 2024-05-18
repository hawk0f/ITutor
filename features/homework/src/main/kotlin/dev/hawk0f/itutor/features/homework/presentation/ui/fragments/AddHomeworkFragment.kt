package dev.hawk0f.itutor.features.homework.presentation.ui.fragments

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.fullText
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.core.presentation.extensions.setupIsEmptyValidator
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.extensions.validateInputs
import dev.hawk0f.itutor.features.homework.R
import dev.hawk0f.itutor.features.homework.databinding.FragmentAddHomeworkBinding
import dev.hawk0f.itutor.features.homework.presentation.ui.viewmodels.AddHomeworkViewModel

@AndroidEntryPoint
class AddHomeworkFragment : BaseFragment<AddHomeworkViewModel, FragmentAddHomeworkBinding>(R.layout.fragment_add_homework)
{
    override val viewModel: AddHomeworkViewModel by viewModels()
    override val binding: FragmentAddHomeworkBinding by viewBinding(FragmentAddHomeworkBinding::bind)

    override fun initialize()
    {
        setupValidators()
    }

    private fun setupValidators() = with(binding) {
        lessonLayout.setupIsEmptyValidator()
        studentLayout.setupIsEmptyValidator()
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
        subscribeToAdd()
    }

    private fun subscribeToHomeworks() = with(binding) {
        viewModel.homeworksState.collectAsUIState(state = {
            it.setupViewVisibilityCircular(group, loader)
        }, onSuccess = {
            val adapter =
                ArrayAdapter(requireContext(), R.layout.dropdown_item, it.map { lessonStudent -> "Дата: " + lessonStudent.date.parseToFormat("d MMMM") + ". Время: " + lessonStudent.startTime + " - " + lessonStudent.endTime }.distinct())
            lessonDropDown.setAdapter(adapter)
            lessonDropDown.setOnItemClickListener { _, _, positionLesson, _ ->
                val lessonId = it[positionLesson].lessonId
                viewModel.setLessonId(lessonId)
                val studentAdapter =
                    ArrayAdapter(requireContext(), R.layout.dropdown_item, it.filter { list -> list.lessonId == lessonId }.map { lessonStudent -> lessonStudent.studentName })
                studentDropDown.setAdapter(studentAdapter)
                studentDropDown.setOnItemClickListener { _, _, positionStudent, _ ->
                    val studentId = it[positionStudent].studentId
                    viewModel.setStudentId(studentId)
                }
                studentDropDown.isEnabled = true
                studentLayout.isEnabled = true
            }
        })
    }

    private fun subscribeToAdd() = with(binding) {
        viewModel.addState.collectAsUIState(state = {
            it.setupViewVisibilityCircular(group, loader, false)
        }, onSuccess = {
            findNavController().popBackStack()
        })
    }

    override fun setupListeners()
    {
        setupAddHomeworkButtonListener()
    }

    private fun setupAddHomeworkButtonListener() = with(binding) {
        btnAddHomework.setOnClickListener {
            validateInputs(Pair(viewModel.validateIsEmpty, lessonLayout), Pair(viewModel.validateIsEmpty, studentLayout)) {
                if (homeworkEditText.fullText.isNotEmpty())
                {
                    viewModel.addHomework(homeworkEditText.fullText)
                }
                else
                {
                    showToastLong("Введите домашнее задание")
                }
            }
        }
    }
}