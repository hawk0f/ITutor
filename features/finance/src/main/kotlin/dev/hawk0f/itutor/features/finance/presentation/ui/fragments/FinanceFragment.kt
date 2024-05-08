package dev.hawk0f.itutor.features.finance.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dev.hawk0f.itutor.features.finance.R
import dev.hawk0f.itutor.features.finance.databinding.FragmentFinanceBinding
import dev.hawk0f.itutor.features.finance.presentation.ui.adapters.FinanceViewPagerAdapter

class FinanceFragment : Fragment(R.layout.fragment_finance)
{
    private val binding: FragmentFinanceBinding by viewBinding(FragmentFinanceBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FinanceViewPagerAdapter(childFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            if (position == 0) tab.text = "Занятия" else tab.text = "Статистика"
        }.attach()
    }
}