package dev.hawk0f.itutor.features.finance.presentation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.hawk0f.itutor.features.finance.presentation.ui.fragments.DiagramFragment
import dev.hawk0f.itutor.features.finance.presentation.ui.fragments.PaymentFragment

class FinanceViewPagerAdapter(fragmentManager: FragmentManager, lifeCycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifeCycle)
{
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment
    {
        return if (position == 0)
        {
            PaymentFragment()
        }
        else
        {
            DiagramFragment()
        }
    }
}