package dev.hawk0f.itutor.core.presentation.widgets

import androidx.activity.compose.LocalActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.hawk0f.itutor.core.R
import dev.hawk0f.itutor.core.presentation.EventManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonScreen(
    topAppBarTitle: String? = null,
    showBottomNav: Boolean = true,
    navHostControllerOverride: NavHostController? = null,
    navIconOverride: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack,
    navBackOverride: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = {
        IconButton(
            onClick = {
                EventManager.triggerEvent(EventManager.AppEvent.NavigateToProfile)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.account_icon),
                contentDescription = null
            )
        }
    },
    content: @Composable (BoxScope.() -> Unit),
) {
    val navHostController = navHostControllerOverride
        ?: rememberNavController()//LocalAppNavController.current
    val activity = LocalActivity.current as AppCompatActivity?

    val navigateBack: () -> Unit = remember {
        {
            if (navBackOverride != null) {
                navBackOverride()
            } else {
                if (!navHostController.navigateUp()) {
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            }
        }
    }

    val cbs by navHostController.currentBackStackEntryAsState()
    val curr = cbs?.destination

    var selectedSection by remember(curr) {
        val navSection =
            if (navHostController.currentDestination?.route?.contains("Students") == true) {
                NavSection.Students
            } else if (navHostController.currentDestination?.route?.contains("Lessons") == true) {
                NavSection.Lessons
            } else if (navHostController.currentDestination?.route?.contains("Homework") == true) {
                NavSection.Homework
            } else if (navHostController.currentDestination?.route?.contains("Finance") == true) {
                NavSection.Finance
            } else {
                NavSection.Notes
            }
        mutableStateOf(navSection)
    }

    Scaffold(
        topBar = {
            topAppBarTitle?.let {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    ),
                    title = {
                        Text(
                            text = topAppBarTitle,
                            style = TextStyle(fontSize = 16.sp),
                            fontWeight = FontWeight(700)
                        )
                    },
                    actions = {
                        actions?.invoke(this)
                    },
                    navigationIcon = {
                        navIconOverride?.let {
                            IconButton(
                                onClick = navigateBack,
                            ) {
                                Icon(
                                    imageVector = navIconOverride,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showBottomNav) {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectedSection == NavSection.Students,
                        onClick = {
                            selectedSection = NavSection.Students
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(R.drawable.students_icon),
                                contentDescription = null
                            )
                        },
                        alwaysShowLabel = false,
                    )
                    NavigationBarItem(
                        selected = selectedSection == NavSection.Lessons,
                        onClick = {
                            selectedSection = NavSection.Lessons
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(R.drawable.schedule_icon),
                                contentDescription = null
                            )
                        },
                        alwaysShowLabel = false,
                    )
                    NavigationBarItem(
                        selected = selectedSection == NavSection.Homework,
                        onClick = {
                            selectedSection = NavSection.Homework
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(R.drawable.homework_icon),
                                contentDescription = null
                            )
                        },
                        alwaysShowLabel = false,
                    )
                    NavigationBarItem(
                        selected = selectedSection == NavSection.Finance,
                        onClick = {
                            selectedSection = NavSection.Finance
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(R.drawable.finance_icon),
                                contentDescription = null
                            )
                        },
                        alwaysShowLabel = false,
                    )
                    NavigationBarItem(
                        selected = selectedSection == NavSection.Notes,
                        onClick = {
                            selectedSection = NavSection.Notes
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(R.drawable.notes_icon),
                                contentDescription = null
                            )
                        },
                        alwaysShowLabel = false,
                    )
                }
            }
        }
    ) { innerValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerValues)
        ) {
            content.invoke(this)
        }
    }
}

private enum class NavSection {
    Students, Lessons, Homework, Finance, Notes
}