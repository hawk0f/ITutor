package dev.hawk0f.itutor.features.lessons.presentation.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.InputType
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.core.presentation.extensions.parseToDate
import dev.hawk0f.itutor.core.presentation.extensions.parseToFormat
import dev.hawk0f.itutor.core.presentation.extensions.parseToTime
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.features.lessons.R
import dev.hawk0f.itutor.features.lessons.databinding.FragmentAddLessonBinding
import dev.hawk0f.itutor.features.lessons.presentation.ui.adapters.LessonStudentsAdapter
import dev.hawk0f.itutor.features.lessons.presentation.ui.viewmodels.AddLessonViewModel
import dev.hawk0f.itutor.navigation.AddLessonFragmentArgs
import dev.hawk0f.itutor.navigation.AddLessonFragmentDirections
import java.time.LocalDate
import java.time.LocalTime

@AndroidEntryPoint
class AddLessonFragment : BaseFragment<AddLessonViewModel, FragmentAddLessonBinding>(R.layout.fragment_add_lesson)
{
    override val viewModel: AddLessonViewModel by viewModels()
    override val binding: FragmentAddLessonBinding by viewBinding(FragmentAddLessonBinding::bind)

    private val studentAdapter = LessonStudentsAdapter {
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
        viewModel.setupFields(args.studentIds?.toCollection(ArrayList()), args.date, args.startTime, args.endTime, args.subjectId, args.subject)
    }

    private fun setupViewModel() = with(binding) {
        viewmodel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    private fun setupRecycler() = with(binding) {
        with(recyclerStudents) {
            layoutManager = LinearLayoutManager(context)
            adapter = studentAdapter
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
        viewModel.subjectState.collectAsUIState(state = {
            it.setupViewVisibility(group, loader)
        }, onSuccess = {
            val adapter = ArrayAdapter(requireContext(), R.layout.subject_item, it.map { subject -> subject.subjectName })
            subjectDropDown.setAdapter(adapter)
            subjectDropDown.setOnItemClickListener { _, _, position, _ ->
                viewModel.setSubjectId(it[position].id)
            }
        })
    }

    private fun subscribeToAdd() = with(binding) {
        viewModel.addState.collectAsUIState(state = {
            it.setupViewVisibility(group, loader, false)
        }, onSuccess = {
            showToastLong("Добавлен")
            val a = findNavController().previousBackStackEntry?.destination
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
            it.setupViewVisibility(group, loader)
        }, onSuccess = { list ->
            val newList = list.toMutableList()
            list.forEach {
                if (!viewModel.getStudentsIds().contains(it.id))
                {
                    newList.remove(it)
                }
            }
            studentAdapter.submitList(newList)
        })
    }

    //TODO разбить на отдельные методы
    override fun setupListeners() = with(binding) {
        //Кнопка добавления учеников
        addStudentBtn.setOnClickListener {
            findNavController().navigateSafely(AddLessonFragmentDirections.actionAddLessonFragmentToStudentBottomSheetFragment(viewModel.getStudentsIds().toIntArray(), viewModel.date, viewModel.startTime, viewModel.endTime, viewModel.getSubjectId(), viewModel.subject))
        }

        dateTv.inputType = InputType.TYPE_NULL
        endTimeTv.inputType = InputType.TYPE_NULL
        startTimeTv.inputType = InputType.TYPE_NULL

        //Дата
        val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            viewModel.date = LocalDate.of(year, monthOfYear + 1, dayOfMonth).parseToFormat("dd.MM.yyyy")
            dateTv.setText(viewModel.date)
        }
        dateTv.setOnClickListener {
            val date = viewModel.date.parseToDate("dd.MM.yyyy")
            DatePickerDialog(requireContext(), datePickerListener, date.year, date.monthValue.minus(1), date.dayOfMonth).show()
        }
        dateTv.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                val date = viewModel.date.parseToDate("dd.MM.yyyy")
                DatePickerDialog(requireContext(), datePickerListener, date.year, date.monthValue.minus(1), date.dayOfMonth).show()
            }
        }

        //Время начала
        val startTimePickerListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.startTime = LocalTime.of(hourOfDay, minute).parseToFormat("HH:mm")
            startTimeTv.setText(viewModel.startTime)
        }
        startTimeTv.setOnClickListener {
            val time = viewModel.startTime.parseToTime("HH:mm")
            TimePickerDialog(requireContext(), startTimePickerListener, time.hour, time.minute, true).show()
        }
        startTimeTv.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                val time = viewModel.startTime.parseToTime("HH:mm")
                TimePickerDialog(requireContext(), startTimePickerListener, time.hour, time.minute, true).show()
            }
        }

        //Время окончания
        val endTimePickerListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.endTime = LocalTime.of(hourOfDay, minute).parseToFormat("HH:mm")
            endTimeTv.setText(viewModel.endTime)
        }
        endTimeTv.setOnClickListener {
            val time = viewModel.endTime.parseToTime("HH:mm")
            TimePickerDialog(requireContext(), endTimePickerListener, time.hour, time.minute, true).show()
        }
        endTimeTv.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                val time = viewModel.endTime.parseToTime("HH:mm")
                TimePickerDialog(requireContext(), endTimePickerListener, time.hour, time.minute, true).show()
            }
        }
    }
}