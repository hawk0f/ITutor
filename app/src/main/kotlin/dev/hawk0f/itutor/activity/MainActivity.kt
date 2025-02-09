package dev.hawk0f.itutor.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import dev.hawk0f.itutor.R
import dev.hawk0f.itutor.core.data.local.UserDataPreferences
import dev.hawk0f.itutor.core.presentation.CurrentUser
import dev.hawk0f.itutor.core.presentation.extensions.initNavController
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    private val navController by lazy { initNavController(dev.hawk0f.itutor.core.R.id.nav_host_fragment) }

    @Inject
    lateinit var userDataPreferences: UserDataPreferences

    override fun onCreate(savedInstanceState: Bundle?)
    {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(dev.hawk0f.itutor.core.R.layout.activity_main)

        setupNavigation()
    }

    private fun setupNavigation()
    {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        val toolbar = findViewById<MaterialToolbar>(dev.hawk0f.itutor.core.R.id.toolbar)

        val userId = userDataPreferences.userId

        if (userId != 0)
        {
            CurrentUser.setUserId(userId)
            navGraph.setStartDestination(R.id.mainContentFragment)
        }

        navController.graph = navGraph

        val appBarConfiguration = AppBarConfiguration.Builder(setOf(R.id.authFragment, R.id.registerFragment, R.id.mainContentFragment, R.id.profileFragment)).build()
        toolbar.setupWithNavController(navController, appBarConfiguration)

        setSupportActionBar(toolbar)
    }
}