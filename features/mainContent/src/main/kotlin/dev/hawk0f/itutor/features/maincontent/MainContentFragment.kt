package dev.hawk0f.itutor.features.maincontent

import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.hawk0f.itutor.core.presentation.base.BaseFlowFragment
import dev.hawk0f.itutor.core.presentation.extensions.toolbar

class MainContentFragment : BaseFlowFragment(R.layout.fragment_main_content, R.id.fragment_container, R.id.bottom_navigation_view)
{
    override fun setupNavigation(navController: NavController, bottomNavigationView: BottomNavigationView)
    {
        val navGraph = navController.navInflater.inflate(R.navigation.main_content_nav_graph)
        navController.graph = navGraph

        val toolbar = toolbar(dev.hawk0f.itutor.core.presentation.R.id.toolbar)
        val appBarConfiguration = AppBarConfiguration.Builder(setOf(R.id.studentsNavGraph)).build()
        toolbar.setupWithNavController(navController, appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)
    }
}