package dev.hawk0f.itutor.homework.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.R
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.fullText
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.databinding.FragmentEditHomeworkBinding
import dev.hawk0f.itutor.homework.presentation.ui.viewmodels.EditHomeworkViewModel

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
            val lessonText = getString(dev.hawk0f.itutor.core.R.string.homework_lesson_info_input, it[indexOfCurrentLesson].date.parseToFormat("d MMMM"), it[indexOfCurrentLesson].startTime + " - " + it[indexOfCurrentLesson].endTime)
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
                showToastLong(dev.hawk0f.itutor.core.R.string.fill_homework_text)
            }
        }
    }
}