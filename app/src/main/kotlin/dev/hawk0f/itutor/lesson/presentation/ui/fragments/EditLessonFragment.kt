package dev.hawk0f.itutor.lesson.presentation.ui.fragments

import android.text.InputType
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker.Builder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.R
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.base.BaseHorizontalDividerItemDecoration
import dev.hawk0f.itutor.core.presentation.base.BaseVerticalDividerItemDecoration
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.core.presentation.extensions.parseToDate
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.core.presentation.extensions.parseToTime
import dev.hawk0f.itutor.core.presentation.extensions.setupIsEmptyValidator
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.extensions.validateInputs
import dev.hawk0f.itutor.databinding.FragmentEditLessonBinding
import dev.hawk0f.itutor.lesson.presentation.ui.viewmodels.EditLessonViewModel
import dev.hawk0f.itutor.lesson.presentation.models.LessonUI
import dev.hawk0f.itutor.lesson.presentation.models.StudentInLessonUI
import dev.hawk0f.itutor.lesson.presentation.ui.adapters.LessonStudentsAdapter
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar

@AndroidEntryPoint
class EditLessonFragment : BaseFragment<EditLessonViewModel, FragmentEditLessonBinding>(R.layout.fragment_edit_lesson)
{
    override val viewModel: EditLessonViewModel by viewModels()
    override val binding: FragmentEditLessonBinding by viewBinding(FragmentEditLessonBinding::bind)

    private var lessonId: Int = 0

    private var lesson: LessonUI? = null

    private val lessonStudentsAdapter = LessonStudentsAdapter {
        viewModel.removeStudentId(it)
        fetchLessonStudents()
    }

    override fun initialize()
    {
        setupFields()
        setupRecycler()
        setupValidators()
    }

    private fun setupFields()
    {
        val args = EditLessonFragmentArgs.fromBundle(requireArguments())
        lessonId = args.lessonId
        lesson = args.lesson

        val oldStudentIds = args.studentIds

        if (lesson != null)
        {
            viewModel.setLesson(lesson!!)
            viewModel.setOldStudentIds(oldStudentIds!!.toCollection(ArrayList()))
            setupViewModel()
            fetchLessonStudents()
        }
        else
        {
            fetchLesson()
        }
    }

    private fun setupViewModel() = with(binding) {
        viewmodel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    private fun setupRecycler() = with(binding) {
        with(recyclerStudents) {
            layoutManager = LinearLayoutManager(context)
            adapter = lessonStudentsAdapter

            itemAnimator = FadeInAnimator()

            addItemDecoration(BaseHorizontalDividerItemDecoration(0))
            addItemDecoration(BaseVerticalDividerItemDecoration(30, 15))
        }
    }

    private fun setupValidators() = with(binding)
    {
        subjectLayout.setupIsEmptyValidator()
    }

    override fun setupRequests()
    {
        fetchSubjects()
    }

    private fun fetchLesson()
    {
        viewModel.getLessonById(lessonId)
    }

    private fun fetchSubjects()
    {
        viewModel.fetchSubjects()
    }

    private fun fetchLessonStudents()
    {
        viewModel.fetchLessonStudents()
    }

    override fun setupSubscribers()
    {
        subscribeToLesson()
        subscribeToSubjects()
        subscribeToLessonStudents()
        subscribeToUpdate()
    }

    private fun subscribeToLesson() = with(binding) {
        viewModel.lessonState.collectAsUIState(state = {
            it.setupViewVisibilityCircular(group, loader)
        }, onSuccess = { lesson ->
            viewModel.setLesson(lesson)
            viewModel.setOldStudentIds(lesson.studentsIds)
            setupViewModel()
            fetchLessonStudents()
        })
    }

    private fun subscribeToSubjects() = with(binding) {
        viewModel.subjectState.collectAsUIState { list ->
            val adapter = ArrayAdapter(requireContext(), R.layout.subject_item, list.map { subject -> subject.subjectName })
            subjectDropDown.setAdapter(adapter)
            subjectDropDown.setOnItemClickListener { _, _, position, _ ->
                viewModel.setSubjectId(list[position].id)
            }
        }
    }

    private fun subscribeToUpdate() = with(binding) {
        viewModel.updateState.collectAsUIState(state = {
            it.setupViewVisibilityCircular(group, loader, false)
        }, onSuccess = {
            showToastLong(dev.hawk0f.itutor.core.R.string.success_lesson_updated)
            findNavController().popBackStack()
        })
    }

    private fun subscribeToLessonStudents()
    {
        viewModel.lessonStudentsState.collectAsUIState { list ->
            viewModel.allStudents.clear()
            viewModel.allStudents.addAll(list)

            updateAdapterList()
        }
    }

    private fun updateAdapterList()
    {
        val list = mutableListOf<StudentInLessonUI>()
        viewModel.allStudents.forEach {
            if (viewModel.getStudentsIds().contains(it.id))
            {
                list.add(it)
            }
        }
        lessonStudentsAdapter.submitList(list)
    }

    override fun setupListeners()
    {
        setupChooseStudentsButton()
        setupDatePicker()
        setupStartTimePicker()
        setupEndTimePicker()
        setupEditLessonButtonListener()
    }

    private fun setupEditLessonButtonListener() = with(binding) {
        btnEditLesson.setOnClickListener {
            validateInputs(Pair(viewModel.validateIsEmpty, subjectLayout)) {
                if (lessonStudentsAdapter.currentList.isEmpty())
                {
                    showToastLong(dev.hawk0f.itutor.core.R.string.choose_students)
                }
                else if (viewModel.isUpdateNeeded())
                {
                    viewModel.updateLesson()
                }
                else
                {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setupChooseStudentsButton() = with(binding) {
        chooseStudentBtn.setOnClickListener {
            findNavController().navigateSafely(EditLessonFragmentDirections.actionEditLessonFragmentToStudentBottomSheetFragment(viewModel.getCurrentLesson()))
        }
    }

    private fun setupDatePicker() = with(binding) {
        dateTv.inputType = InputType.TYPE_NULL
        val selectedDateInMilliseconds =
            viewModel.parsedDate.parseToDate("dd.MM.yyyy").atStartOfDay(ZoneId.systemDefault())
                .toInstant().toEpochMilli()

        val builderDate = Builder.datePicker().setSelection(selectedDateInMilliseconds).build()

        dateTv.setOnClickListener {
            builderDate.show(childFragmentManager, "Date")
        }
        dateTv.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                builderDate.show(childFragmentManager, "Date")
            }
        }

        builderDate.addOnPositiveButtonClickListener {
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            viewModel.date = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))
            viewModel.parsedDate = viewModel.date.parseToFormat("dd.MM.yyyy")

            dateTv.setText(viewModel.parsedDate)
        }
    }

    private fun setupStartTimePicker() = with(binding) {
        val selectedTime = viewModel.startTime.parseToTime("HH:mm")

        val builderTime = provideTimePicker(selectedTime, startTimeTv, "StartTime")

        builderTime.addOnPositiveButtonClickListener {
            viewModel.startTime =
                LocalTime.of(builderTime.hour, builderTime.minute).parseToFormat("HH:mm")
            startTimeTv.setText(viewModel.startTime)
        }
    }

    private fun setupEndTimePicker() = with(binding) {
        val selectedTime = viewModel.endTime.parseToTime("HH:mm")

        val builderTime = provideTimePicker(selectedTime, endTimeTv, "EndTime")

        builderTime.addOnPositiveButtonClickListener {
            viewModel.endTime =
                LocalTime.of(builderTime.hour, builderTime.minute).parseToFormat("HH:mm")
            endTimeTv.setText(viewModel.endTime)
        }
    }

    private fun provideTimePicker(selectedTime: LocalTime, timeTv: TextInputEditText, tag: String): MaterialTimePicker
    {
        timeTv.inputType = InputType.TYPE_NULL

        val builderTime = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(selectedTime.hour).setMinute(selectedTime.minute)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK).build()

        timeTv.setOnClickListener {
            builderTime.show(childFragmentManager, tag)
        }
        timeTv.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                builderTime.show(childFragmentManager, tag)
            }
        }

        return builderTime
    }
}