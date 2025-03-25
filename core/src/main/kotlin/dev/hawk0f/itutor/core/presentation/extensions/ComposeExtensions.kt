package dev.hawk0f.itutor.core.presentation.extensions

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface SnackbarProvider {
    fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        withDismissAction: Boolean = false,
        onAction: (() -> Unit)? = null,
    )
}

class SnackbarProviderImpl(
    private val snackbarHostState: SnackbarHostState,
    private val scope: CoroutineScope,
) : SnackbarProvider {
    override fun showSnackbar(
        message: String,
        actionLabel: String?,
        duration: SnackbarDuration,
        withDismissAction: Boolean,
        onAction: (() -> Unit)?,
    ) {
        scope.launch {
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = duration,
                withDismissAction = withDismissAction
            )
            when (result) {
                SnackbarResult.ActionPerformed -> onAction?.invoke()
                SnackbarResult.Dismissed -> {}
            }
        }
    }
}

val LocalSnackbarProvider: ProvidableCompositionLocal<SnackbarProvider> =
    compositionLocalOf { error("No SnackbarProvider provided") }

@Composable
fun ProvideSnackbarProvider(
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    content: @Composable () -> Unit,
) {
    val snackbarProvider = remember(snackbarHostState, coroutineScope) {
        SnackbarProviderImpl(snackbarHostState, coroutineScope)
    }
    CompositionLocalProvider(LocalSnackbarProvider provides snackbarProvider, content = content)
}

fun SnackbarProvider.showShortSnackbar(message: String) {
    showSnackbar(message = message, duration = SnackbarDuration.Short)
}

fun SnackbarProvider.showLongSnackbar(message: String) {
    showSnackbar(message = message, duration = SnackbarDuration.Long)
}

fun SnackbarProvider.showIndefiniteSnackbar(message: String) {
    showSnackbar(message = message, duration = SnackbarDuration.Indefinite)
}

fun SnackbarProvider.showSnackbarWithAction(
    message: String,
    actionLabel: String,
    onAction: () -> Unit,
) {
    showSnackbar(message, actionLabel, onAction = onAction)
}

val LocalAppNavController = compositionLocalOf<NavHostController> {
    error("No LocalAppNavController")
}