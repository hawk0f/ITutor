package dev.hawk0f.itutor.features.homework.presentation.ui.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.base.BaseHorizontalDividerItemDecoration
import dev.hawk0f.itutor.core.presentation.base.BaseVerticalDividerItemDecoration
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.features.homework.R
import dev.hawk0f.itutor.features.homework.databinding.FragmentHomeworkBinding
import dev.hawk0f.itutor.features.homework.presentation.ui.adapters.StudentHomeworksAdapter
import dev.hawk0f.itutor.features.homework.presentation.ui.viewmodels.HomeworkViewModel
import dev.hawk0f.itutor.navigation.HomeworkFragmentDirections
import jp.wasabeef.recyclerview.animators.FadeInAnimator

@AndroidEntryPoint
class HomeworkFragment : BaseFragment<HomeworkViewModel, FragmentHomeworkBinding>(R.layout.fragment_homework)
{
    override val viewModel: HomeworkViewModel by viewModels()
    override val binding: FragmentHomeworkBinding by viewBinding(FragmentHomeworkBinding::bind)

    private val studentHomeworksAdapter =
        StudentHomeworksAdapter(onHomeworkClick = { studentId, lessonId, homework ->
            findNavController().navigateSafely(HomeworkFragmentDirections.actionHomeworkFragmentToEditHomeworkFragment(studentId, lessonId, homework))
        }, onUpdateClick = { studentId, lessonId, isHomeworkDone ->
            viewModel.updateHomeworkStatus(studentId, lessonId, isHomeworkDone)
        })

    override fun initialize()
    {
        setupRecycler()
    }

    private fun setupRecycler() = with(binding) {
        with(recyclerStudentHomeworks) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = studentHomeworksAdapter

            itemAnimator = FadeInAnimator()

            addItemDecoration(BaseHorizontalDividerItemDecoration(40))
            addItemDecoration(BaseVerticalDividerItemDecoration(30, 10))
        }
    }

    override fun setupRequests()
    {
        fetchLessonStudents()
    }

    private fun fetchLessonStudents()
    {
        viewModel.fetchLessonStudents()
    }

    override fun setupSubscribers()
    {
        subscribeToLessonStudents()
        subscribeToUpdate()
    }

    private fun subscribeToLessonStudents() = with(binding) {
        viewModel.lessonStudentsState.collectAsUIState(state = {
            it.setupViewVisibilityLinear(group, loader)
        }, onSuccess = {
            if (it.isEmpty())
            {
                noHomeworks.visibility = View.VISIBLE
                recyclerStudentHomeworks.visibility = View.GONE
            }
            else
            {
                studentHomeworksAdapter.submitList(it)
            }
        })
    }

    private fun subscribeToUpdate()
    {
        viewModel.updateState.collectAsUIState { }
    }

    override fun setupListeners()
    {
        setupAddButtonListener()
    }

    private fun setupAddButtonListener() = with(binding) {
        btnAddHomework.setOnClickListener {
            findNavController().navigateSafely(HomeworkFragmentDirections.actionHomeworkFragmentToAddHomeworkFragment())
        }
    }
}