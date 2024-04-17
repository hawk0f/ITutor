package dev.hawk0f.itutor.features.students.presentation.ui

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.hawk0f.itutor.core.presentation.base.BaseFragment
import dev.hawk0f.itutor.features.students.R
import dev.hawk0f.itutor.features.students.databinding.FragmentStudentsBinding

class StudentsFragment : BaseFragment<StudentsViewModel, FragmentStudentsBinding>(R.layout.fragment_students)
{
    override val viewModel: StudentsViewModel by viewModels()
    override val binding: FragmentStudentsBinding by viewBinding(FragmentStudentsBinding::bind)
}