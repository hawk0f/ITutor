package dev.hawk0f.itutor.features.lessons.presentation.ui.fragments

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseBottomSheet
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.features.lessons.R
import dev.hawk0f.itutor.features.lessons.databinding.FragmentStudentBottomSheetBinding
import dev.hawk0f.itutor.features.lessons.presentation.ui.adapters.ChooseStudentAdapter
import dev.hawk0f.itutor.features.lessons.presentation.ui.viewmodels.StudentBottomSheetViewModel
import dev.hawk0f.itutor.navigation.R.id.action_studentBottomSheetFragment_to_addLessonFragment

private const val ARG_PARAM1 = "studentIds"

@AndroidEntryPoint
class StudentBottomSheetFragment : BaseBottomSheet<StudentBottomSheetViewModel, FragmentStudentBottomSheetBinding>(R.layout.fragment_student_bottom_sheet)
{
    override val viewModel: StudentBottomSheetViewModel by viewModels()
    override val binding: FragmentStudentBottomSheetBinding by viewBinding(FragmentStudentBottomSheetBinding::bind)

    private var previousStudentIds = ArrayList<Int>()

    private var studentIds = previousStudentIds

    private val chooseStudentsAdapter = ChooseStudentAdapter {
        if (studentIds.contains(it))
        {
            studentIds.remove(it)
        }
        else
        {
            studentIds.add(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
            previousStudentIds = it.getIntegerArrayList(ARG_PARAM1) ?: ArrayList()
            studentIds = previousStudentIds
        }
    }

    override fun initialize()
    {
        setupRecycler()
    }

    private fun setupRecycler() = with(binding) {
        with(recyclerStudents) {
            layoutManager = LinearLayoutManager(context)
            adapter = chooseStudentsAdapter
        }
    }

    override fun setupListeners()
    {
        setupSaveButton()
        setupCancelButton()
    }

    private fun setupSaveButton() = with(binding) {
        btnSave.setOnClickListener {
            if (studentIds.isNotEmpty())
            {
                findNavController().navigateSafely(object : NavDirections
                {
                    override val actionId: Int = action_studentBottomSheetFragment_to_addLessonFragment
                    override val arguments: Bundle = bundleOf(ARG_PARAM1 to studentIds)
                })
            }
            else
            {
                showToastLong("Выберите учеников")
            }
        }
    }

    private fun setupCancelButton() = with(binding) {
        btnCancel.setOnClickListener {
            findNavController().navigateSafely(object : NavDirections
            {
                override val actionId: Int = action_studentBottomSheetFragment_to_addLessonFragment
                override val arguments: Bundle = bundleOf(ARG_PARAM1 to previousStudentIds)
            })
        }
    }

    override fun setupRequests()
    {
        fetchStudents()
    }

    private fun fetchStudents()
    {
        viewModel.getLessonStudents(studentIds)
    }

    override fun setupSubscribers()
    {
        subscribeToLessonStudents()
    }

    private fun subscribeToLessonStudents() = with(binding) {
        viewModel.lessonStudentsState.collectAsUIState(state = {
            it.setupViewVisibility(group, loader)
        }, onSuccess = { list ->
            list.forEach {
                it.isSelected = previousStudentIds.contains(it.id)
            }
            chooseStudentsAdapter.submitList(list)
        })
    }
}