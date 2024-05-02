package dev.hawk0f.itutor.features.lessons.presentation.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import dev.hawk0f.itutor.features.lessons.databinding.FragmentAddLessonBinding
import dev.hawk0f.itutor.features.lessons.presentation.ui.viewmodels.AddLessonViewModel
import dev.hawk0f.itutor.features.lessons.R
import dev.hawk0f.itutor.features.lessons.presentation.ui.adapters.SubjectAdapter
import java.time.LocalDate
import java.time.LocalTime

@AndroidEntryPoint
class AddLessonFragment : BaseFragment<AddLessonViewModel, FragmentAddLessonBinding>(R.layout.fragment_add_lesson)
{
    override val viewModel: AddLessonViewModel by viewModels()
    override val binding: FragmentAddLessonBinding by viewBinding(FragmentAddLessonBinding::bind)

    override fun initialize()
    {
        setupFields()
        setupViewModel()
    }

    private fun setupFields()
    {
        viewModel.clearFields()
    }

    private fun setupViewModel() = with(binding) {
        viewmodel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    override fun setupRequests()
    {
        fetchSubjects()
    }

    private fun fetchSubjects()
    {
        viewModel.fetchSubjects()
    }

    override fun setupSubscribers()
    {
        subscribeToSubjects()
        subscribeToAdd()
    }

    private fun subscribeToSubjects() = with(binding) {
        viewModel.subjectState.collectAsUIState(state = {
            it.setupViewVisibility(group, loader)
        }, onSuccess = {
            val adapter = SubjectAdapter {
                viewModel.setSubjectId(it)
            }
        })
    }

    private fun subscribeToAdd() = with(binding) {
        viewModel.addState.collectAsUIState(state = {
            it.setupViewVisibility(group, loader)
        }, onSuccess = {
            showToastLong("Добавлен")
            findNavController().popBackStack()
        })
    }

    override fun setupListeners() = with(binding) {
        addStudentBtn.setOnClickListener {
            //TODO открывать BottomSheet со списком учеников
        }

        val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            viewModel.date = "$dayOfMonth.${monthOfYear + 1}.$year"
            dateTv.setText(viewModel.date)
        }
        dateTv.setOnClickListener {
            DatePickerDialog(requireContext(), datePickerListener, LocalDate.parse(viewModel.date).year, LocalDate.parse(viewModel.date).monthValue.minus(1), LocalDate.parse(viewModel.date).dayOfMonth).show()
        }
        dateTv.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                DatePickerDialog(requireContext(), datePickerListener, LocalDate.parse(viewModel.date).year, LocalDate.parse(viewModel.date).monthValue.minus(1), LocalDate.parse(viewModel.date).dayOfMonth).show()
            }
        }

        val startTimePickerListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.startTime = "$hourOfDay:$minute"
            startTimeTv.setText(viewModel.startTime)
        }
        startTimeTv.setOnClickListener {
            TimePickerDialog(requireContext(), startTimePickerListener, LocalTime.parse(viewModel.startTime).hour, LocalTime.parse(viewModel.startTime).minute, true).show()
        }
        startTimeTv.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                TimePickerDialog(requireContext(), startTimePickerListener, LocalTime.parse(viewModel.startTime).hour, LocalTime.parse(viewModel.startTime).minute, true).show()
            }
        }

        val endTimePickerListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.endTime = "$hourOfDay:$minute"
            endTimeTv.setText(viewModel.endTime)
        }
        endTimeTv.setOnClickListener {
            TimePickerDialog(requireContext(), endTimePickerListener, LocalTime.parse(viewModel.endTime).hour, LocalTime.parse(viewModel.endTime).minute, true).show()
        }
        endTimeTv.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
            {
                TimePickerDialog(requireContext(), endTimePickerListener, LocalTime.parse(viewModel.endTime).hour, LocalTime.parse(viewModel.endTime).minute, true).show()
            }
        }
    }
}