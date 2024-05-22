package dev.hawk0f.itutor.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.R.*
import dev.hawk0f.itutor.R.navigation.nav_graph
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.NavSetupper
import dev.hawk0f.itutor.core.presentation.R.id.nav_host_fragment
import dev.hawk0f.itutor.core.presentation.R.layout.activity_main
import dev.hawk0f.itutor.core.presentation.extensions.initNavController
import dev.hawk0f.itutor.core.presentation.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavSetupper
{
    private val navController by lazy { initNavController(nav_host_fragment) }

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        setupNavigation()
    }

    private fun setupNavigation()
    {
        val navGraph = navController.navInflater.inflate(nav_graph)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)

        val userId = viewModel.userDataPreferences.userId

        if (userId != 0)
        {
            CurrentUser.setUserId(userId)
            navGraph.setStartDestination(id.mainContentFragment)
        }

        navController.graph = navGraph
        
        setupOuterNavGraph()
        setSupportActionBar(toolbar)
    }

    override fun setupOuterNavGraph()
    {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val appBarConfiguration = AppBarConfiguration.Builder(setOf(id.authFragment, id.registerFragment, id.mainContentFragment)).build()
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}