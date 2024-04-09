package dev.hawk0f.itutor.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import dev.hawk0f.core.presentation.R.layout.activity_main
import dev.hawk0f.core.presentation.R.id.nav_host_fragment
import dev.hawk0f.itutor.R.navigation.nav_graph
import androidx.core.view.WindowInsetsCompat
import dev.hawk0f.core.presentation.extensions.initNavController

class MainActivity : AppCompatActivity()
{
    private val navController by lazy { initNavController(nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(nav_host_fragment)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupNavigation()
    }

    private fun setupNavigation()
    {
        val navGraph = navController.navInflater.inflate(nav_graph)
        navController.graph = navGraph
    }
}