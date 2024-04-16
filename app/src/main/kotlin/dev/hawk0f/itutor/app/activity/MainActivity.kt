package dev.hawk0f.itutor.app.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.R.navigation.nav_graph
import dev.hawk0f.itutor.core.presentation.R.id.nav_host_fragment
import dev.hawk0f.itutor.core.presentation.R.layout.activity_main
import dev.hawk0f.itutor.core.presentation.extensions.initNavController

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    private val navController by lazy { initNavController(nav_host_fragment) }

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
        navController.graph = navGraph
    }
}