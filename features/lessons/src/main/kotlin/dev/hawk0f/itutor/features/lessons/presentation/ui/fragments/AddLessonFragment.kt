package dev.hawk0f.itutor.features.lessons.presentation.ui.fragments

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
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.core.presentation.extensions.parseToDate
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.core.presentation.extensions.parseToTime
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.core.presentation.models.LessonStudentUI
import dev.hawk0f.itutor.features.lessons.R
import dev.hawk0f.itutor.features.lessons.databinding.FragmentAddLessonBinding
import dev.hawk0f.itutor.features.lessons.presentation.ui.adapters.LessonStudentsAdapter
import dev.hawk0f.itutor.features.lessons.presentation.ui.viewmodels.AddLessonViewModel
import dev.hawk0f.itutor.navigation.AddLessonFragmentArgs
import dev.hawk0f.itutor.navigation.AddLessonFragmentDirections
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar

@AndroidEntryPoint
class AddLessonFragment : BaseFragment<AddLessonViewModel, FragmentAddLessonBinding>(R.layout.fragment_add_lesson)
{
    override val viewModel: AddLessonViewModel by viewModels()
    override val binding: FragmentAddLessonBinding by viewBinding(FragmentAddLessonBinding::bind)

    private val lessonStudentsAdapter = LessonStudentsAdapter {
        viewModel.removeStudentId(it)
        fetchLessonStudents()
    }

    override fun initialize()
    {
        setupFields()
        setupViewModel()
        setupRecycler()
    }

    private fun setupFields()
    {
        val args = AddLessonFragmentArgs.fromBundle(requireArguments())
        viewModel.setupFields(args.lesson)
    }

    private fun setupViewModel() = with(binding) {
        viewmodel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    private fun setupRecycler() = with(binding) {
        with(recyclerStudents) {
            layoutManager = LinearLayoutManager(context)
            adapter = lessonStudentsAdapter
        }
    }

    override fun setupRequests()
    {
        fetchSubjects()
        fetchLessonStudents()
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
        subscribeToSubjects()
        subscribeToLessonStudents()
        subscribeToAdd()
        subscribeToError()
    }

    private fun subscribeToSubjects() = with(binding) {
        viewModel.subjectState.collectAsUIState {
            val adapter = ArrayAdapter(requireContext(), R.layout.subject_item, it.map { subject -> subject.subjectName })
            subjectDropDown.setAdapter(adapter)
            subjectDropDown.setOnItemClickListener { _, _, position, _ ->
                viewModel.setSubjectId(it[position].id)
            }
        }
    }

    private fun subscribeToAdd() = with(binding) {
        viewModel.addState.collectAsUIState(state = {
            it.setupViewVisibilityCircular(group, loader, false)
        }, onSuccess = {
            showToastLong("Добавлен")
            findNavController().popBackStack()
        })
    }

    private fun subscribeToError()
    {
        viewModel.errorState.observe(viewLifecycleOwner) {
            it?.let {
                showToastLong(it)
                viewModel.clearErrorText()
            }
        }
    }

    private fun subscribeToLessonStudents() = with(binding) {
        viewModel.lessonStudentsState.collectAsUIState(state = {
            it.setupViewVisibilityCircular(group, loader)
        }, onSuccess = { list ->
            viewModel.allStudents.clear()
            viewModel.allStudents.addAll(list)

            updateAdapterList()
        })
    }

    private fun updateAdapterList()
    {
        val list = ArrayList<LessonStudentUI>()
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
    }

    private fun setupChooseStudentsButton() = with(binding) {
        chooseStudentBtn.setOnClickListener {
            findNavController().navigateSafely(AddLessonFragmentDirections.actionAddLessonFragmentToStudentBottomSheetFragment(viewModel.getCurrentLesson()))
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
            calendar.setTimeInMillis(it)
            val date =
                LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))
            viewModel.parsedDate = date.parseToFormat("dd.MM.yyyy")
            viewModel.date = date

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