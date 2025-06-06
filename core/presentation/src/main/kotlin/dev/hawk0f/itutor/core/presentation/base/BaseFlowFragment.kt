package dev.hawk0f.itutor.core.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.hawk0f.itutor.core.presentation.extensions.flowNavController

/**
 * Base class for FlowFragments
 *
 * FlowFragment is [Fragment] that performs the functions of an activity in Single Activity Architecture, with its own
 * [FragmentContainerView][androidx.fragment.app.FragmentContainerView],
 * [NavGraph][androidx.navigation.NavGraph] and
 * [NavController][androidx.navigation.NavController]
 *
 * @param layoutId fragment layout id
 * @param navHostFragmentId id [FragmentContainerView][androidx.fragment.app.FragmentContainerView]
 * @see [flowNavController]
 */
abstract class BaseFlowFragment(@LayoutRes layoutId: Int, @IdRes private val navHostFragmentId: Int, @IdRes private val bnvId: Int) : Fragment(layoutId)
{
    final override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val bottomNavigationView = view.findViewById<BottomNavigationView>(bnvId)
        val navHostFragment = childFragmentManager.findFragmentById(navHostFragmentId) as NavHostFragment
        val navController = navHostFragment.navController

        setupNavigation(navController, bottomNavigationView)
    }

    protected open fun setupNavigation(navController: NavController, bottomNavigationView: BottomNavigationView)
    {
    }
}