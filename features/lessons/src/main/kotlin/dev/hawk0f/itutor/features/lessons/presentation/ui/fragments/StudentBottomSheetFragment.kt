package dev.hawk0f.itutor.features.lessons.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.R.*
import dev.hawk0f.itutor.core.presentation.base.BaseBottomSheet
import dev.hawk0f.itutor.core.presentation.base.BaseHorizontalDividerItemDecoration
import dev.hawk0f.itutor.core.presentation.base.BaseVerticalDividerItemDecoration
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.models.LessonUI
import dev.hawk0f.itutor.features.lessons.R
import dev.hawk0f.itutor.features.lessons.databinding.FragmentStudentBottomSheetBinding
import dev.hawk0f.itutor.features.lessons.presentation.ui.adapters.ChooseStudentAdapter
import dev.hawk0f.itutor.features.lessons.presentation.ui.viewmodels.StudentBottomSheetViewModel
import dev.hawk0f.itutor.navigation.R.id.editLessonFragment
import dev.hawk0f.itutor.navigation.StudentBottomSheetFragmentArgs
import dev.hawk0f.itutor.navigation.StudentBottomSheetFragmentDirections
import jp.wasabeef.recyclerview.animators.FadeInAnimator

@AndroidEntryPoint
class StudentBottomSheetFragment : BaseBottomSheet<StudentBottomSheetViewModel, FragmentStudentBottomSheetBinding>(R.layout.fragment_student_bottom_sheet)
{
    override val viewModel: StudentBottomSheetViewModel by viewModels()
    override val binding: FragmentStudentBottomSheetBinding by viewBinding(FragmentStudentBottomSheetBinding::bind)

    private lateinit var studentIds: ArrayList<Int>

    private lateinit var lesson: LessonUI

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

    override fun initialize()
    {
        setupIds()
        setupRecycler()
    }

    private fun setupIds()
    {
        val args = StudentBottomSheetFragmentArgs.fromBundle(requireArguments())
        lesson = args.lesson
        studentIds = lesson.studentsIds.toCollection(ArrayList())
    }

    private fun setupRecycler() = with(binding) {
        with(recyclerStudents) {
            layoutManager = LinearLayoutManager(context)
            adapter = chooseStudentsAdapter

            itemAnimator = FadeInAnimator()

            addItemDecoration(BaseHorizontalDividerItemDecoration(0))
            addItemDecoration(BaseVerticalDividerItemDecoration(15, 5))
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
                val oldStudentIds = ArrayList(lesson.studentsIds)
                lesson.studentsIds.clear()
                lesson.studentsIds.addAll(studentIds)
                if (findNavController().previousBackStackEntry?.destination?.id == editLessonFragment)
                {
                    findNavController().navigateSafely(StudentBottomSheetFragmentDirections.actionStudentBottomSheetFragmentToEditLessonFragment(lesson = lesson, studentIds = oldStudentIds.toIntArray()))
                }
                else
                {
                    findNavController().navigateSafely(StudentBottomSheetFragmentDirections.actionStudentBottomSheetFragmentToAddLessonFragment(lesson = lesson))
                }
            }
            else
            {
                showToastLong(string.choose_students)
            }
        }
    }

    private fun setupCancelButton() = with(binding) {
        btnCancel.setOnClickListener {
            if (findNavController().previousBackStackEntry?.destination?.id == editLessonFragment)
            {
                findNavController().navigateSafely(StudentBottomSheetFragmentDirections.actionStudentBottomSheetFragmentToEditLessonFragment(lesson = lesson, studentIds = studentIds.toIntArray()))
            }
            else
            {
                findNavController().navigateSafely(StudentBottomSheetFragmentDirections.actionStudentBottomSheetFragmentToAddLessonFragment(lesson = lesson))
            }
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
                it.isSelected = studentIds.contains(it.id)
            }
            chooseStudentsAdapter.submitList(list)
        })
    }
}