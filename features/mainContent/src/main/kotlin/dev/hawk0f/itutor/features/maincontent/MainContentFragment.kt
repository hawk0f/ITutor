package dev.hawk0f.itutor.features.maincontent

import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.hawk0f.itutor.core.presentation.R.id.toolbar
import dev.hawk0f.itutor.core.presentation.base.BaseFlowFragment
import dev.hawk0f.itutor.core.presentation.extensions.activityNavController
import dev.hawk0f.itutor.core.presentation.extensions.activityToolbar
import dev.hawk0f.itutor.core.presentation.extensions.navigateSafely
import dev.hawk0f.itutor.core.presentation.extensions.parentFragmentInNavHost
import dev.hawk0f.itutor.navigation.MainContentFragmentDirections

class MainContentFragment : BaseFlowFragment(R.layout.fragment_main_content, R.id.fragment_container, R.id.bottom_navigation_view)
{
    override fun setupNavigation(navController: NavController, bottomNavigationView: BottomNavigationView)
    {
        val navGraph = navController.navInflater.inflate(R.navigation.main_content_nav_graph)
        navController.graph = navGraph

        val toolbar = activityToolbar(toolbar)
        val appBarConfiguration = AppBarConfiguration.Builder(setOf(R.id.studentsNavGraph, R.id.lessonsNavGraph, R.id.homeworkNavGraph, R.id.financeFragment, R.id.notesNavGraph)).build()

        toolbar.setupWithNavController(navController, appBarConfiguration)

        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.profile ->
            {
                activityNavController().navigateSafely(MainContentFragmentDirections.actionMainContentFragmentToProfileFragment())
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}