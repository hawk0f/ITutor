package dev.hawk0f.itutor.features.lessons.presentation.ui.fragments

import androidx.fragment.app.viewModels
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
import dev.hawk0f.itutor.navigation.StudentBottomSheetFragmentArgs
import dev.hawk0f.itutor.navigation.StudentBottomSheetFragmentDirections

@AndroidEntryPoint
class StudentBottomSheetFragment : BaseBottomSheet<StudentBottomSheetViewModel, FragmentStudentBottomSheetBinding>(R.layout.fragment_student_bottom_sheet)
{
    override val viewModel: StudentBottomSheetViewModel by viewModels()
    override val binding: FragmentStudentBottomSheetBinding by viewBinding(FragmentStudentBottomSheetBinding::bind)

    private lateinit var studentIds: ArrayList<Int>
    private lateinit var previousList: ArrayList<Int>
    private lateinit var date: String
    private lateinit var startTime: String
    private lateinit var endTime: String
    private var subjectId: Int = 0
    private lateinit var subject: String

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
        previousList = args.studentIds.toCollection(ArrayList())
        studentIds = previousList
        date = args.date
        startTime = args.startTime
        endTime = args.endTime
        subjectId = args.subjectId
        subject = args.subject
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
                findNavController().navigateSafely(StudentBottomSheetFragmentDirections.actionStudentBottomSheetFragmentToAddLessonFragment(studentIds.toIntArray(), date, startTime, endTime, subjectId, subject))
            }
            else
            {
                showToastLong("Выберите учеников")
            }
        }
    }

    private fun setupCancelButton() = with(binding) {
        btnCancel.setOnClickListener {
            findNavController().navigateSafely(StudentBottomSheetFragmentDirections.actionStudentBottomSheetFragmentToAddLessonFragment(previousList.toIntArray(), date, startTime, endTime, subjectId, subject))
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