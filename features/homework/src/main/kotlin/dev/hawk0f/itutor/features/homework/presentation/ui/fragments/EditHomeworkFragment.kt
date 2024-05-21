package dev.hawk0f.itutor.features.homework.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.R.string
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.fullText
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
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
        viewModel.setOldHomework(args.homework)
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
            val lessonText = getString(string.date_custom_input) + it[indexOfCurrentLesson].date.parseToFormat("d MMMM") + getString(string.time_custom_input) + it[indexOfCurrentLesson].startTime + " - " + it[indexOfCurrentLesson].endTime
            lessonEditText.setText(lessonText)

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
                if (viewModel.isUpdateNeeded())
                {
                    viewModel.updateHomework()
                }
                else
                {
                    findNavController().popBackStack()
                }
            }
            else
            {
                showToastLong(string.fill_homework_text)
            }
        }
    }
}