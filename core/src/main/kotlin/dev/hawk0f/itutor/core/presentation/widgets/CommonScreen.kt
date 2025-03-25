package dev.hawk0f.itutor.core.presentation.widgets

import androidx.activity.compose.LocalActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.hawk0f.itutor.core.presentation.extensions.LocalAppNavController
import dev.hawk0f.itutor.core.presentation.extensions.ProvideSnackbarProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonScreen(
    topAppBarTitle: String? = null,
    showBottomNav: Boolean = true,
    navHostControllerOverride: NavHostController? = null,
    navIconOverride: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack,
    navBackOverride: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    bottomAppBarButtons: @Composable (RowScope.() -> Unit)?,
    content: @Composable (BoxScope.() -> Unit),
) {
    val navHostController = navHostControllerOverride ?: LocalAppNavController.current
    val activity = LocalActivity.current as AppCompatActivity

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val navigateBack: () -> Unit = remember {
        {
            if (navBackOverride != null) {
                navBackOverride()
            } else {
                if (!navHostController.navigateUp()) {
                    activity.onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

    ProvideSnackbarProvider(snackbarHostState, scope) {
        Scaffold(
            modifier = Modifier,
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
                if (showBottomNav && bottomAppBarButtons != null) {
                    NavigationBar {
                        bottomAppBarButtons.invoke(this)
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
}