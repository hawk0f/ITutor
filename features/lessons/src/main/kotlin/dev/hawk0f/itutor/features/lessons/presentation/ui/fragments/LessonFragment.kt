package dev.hawk0f.itutor.features.lessons.presentation.ui.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.features.lessons.R
import dev.hawk0f.itutor.features.lessons.databinding.FragmentLessonBinding
import dev.hawk0f.itutor.features.lessons.presentation.ui.adapters.DateLessonsAdapter
import dev.hawk0f.itutor.features.lessons.presentation.ui.viewmodels.LessonViewModel
import dev.hawk0f.itutor.navigation.LessonFragmentDirections

@AndroidEntryPoint
class LessonFragment : BaseFragment<LessonViewModel, FragmentLessonBinding>(R.layout.fragment_lesson)
{
    override val viewModel: LessonViewModel by viewModels()
    override val binding: FragmentLessonBinding by viewBinding(FragmentLessonBinding::bind)

    private val dateLessonsAdapter = DateLessonsAdapter({
        findNavController().navigateSafely(LessonFragmentDirections.actionLessonFragmentToEditLessonFragment(it))
    }, {
        viewModel.deleteLesson(it)
    })

    override fun initialize()
    {
        setupRecycler()
    }

    private fun setupRecycler() = with(binding) {
        with(recyclerDateLessons) {
            layoutManager = LinearLayoutManager(context)
            adapter = dateLessonsAdapter
        }
    }

    override fun setupListeners()
    {
        setupAddLessonButton()
    }

    private fun setupAddLessonButton() = with(binding) {
        btnAddLesson.setOnClickListener {
            findNavController().navigateSafely(LessonFragmentDirections.actionLessonFragmentToAddLessonFragment())
        }
    }

    override fun setupRequests()
    {
        fetchLessons()
    }

    private fun fetchLessons()
    {
        viewModel.fetchLessons()
    }

    override fun setupSubscribers()
    {
        subscribeToLessons()
        subscribeToDelete()
    }

    private fun subscribeToLessons() = with(binding) {
        viewModel.lessonState.collectAsUIState(state = {
            it.setupViewVisibility(group, loader)
        }, onSuccess = { list ->
            dateLessonsAdapter.submitList(list)
        })
    }

    private fun subscribeToDelete()
    {
        viewModel.deleteState.collectAsUIState { fetchLessons() }
    }
}