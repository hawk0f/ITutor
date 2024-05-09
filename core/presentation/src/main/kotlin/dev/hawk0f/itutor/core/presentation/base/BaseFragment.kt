package dev.hawk0f.itutor.core.presentation.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import dev.hawk0f.itutor.core.domain.NetworkError
import dev.hawk0f.itutor.core.presentation.UIState
import dev.hawk0f.itutor.core.presentation.extensions.launchAndCollectIn
import dev.hawk0f.itutor.core.presentation.extensions.screenInputs
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import kotlinx.coroutines.flow.StateFlow

/**
 * Base class for [Fragment]s that work with data
 */
abstract class BaseFragment<ViewModel : BaseViewModel, Binding : ViewBinding>(@LayoutRes layoutId: Int) : Fragment(layoutId)
{
    protected abstract val viewModel: ViewModel
    protected abstract val binding: Binding

    private val screenInputs by lazy { binding.screenInputs }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setupListeners()
        setupRequests()
        setupSubscribers()
    }

    protected open fun initialize()
    {
    }

    protected open fun setupListeners()
    {
    }

    protected open fun setupRequests()
    {
    }

    protected open fun setupSubscribers()
    {
    }

    /**
     * Collect [UIState] with [launchAndCollectIn]
     *
     * @receiver [StateFlow] with [UIState]
     *
     * @param state optional, for working with all states
     * @param onError for error handling
     * @param onSuccess for working with data
     */
    protected fun <T> StateFlow<UIState<T>>.collectAsUIState(lifecycleState: Lifecycle.State = Lifecycle.State.STARTED, state: ((UIState<T>) -> Unit)? = null, onError: ((error: NetworkError) -> Unit)? = null, onSuccess: ((data: T) -> Unit)) = launchAndCollectIn(viewLifecycleOwner, lifecycleState) {
        state?.invoke(it)
        when (it)
        {
            is UIState.Idle ->
            {
            }

            is UIState.Loading ->
            {
            }

            is UIState.Error ->
            {
                onError?.invoke(it.error)
                it.error.setupApiErrors()
            }

            is UIState.Success ->
            {
                onSuccess.invoke(it.data)
            }
        }
    }

    /**
     * Setup views visibility depending on [UIState] states.
     *
     * @receiver [UIState]
     *
     * @param showViewIfSuccess whether to show views if the request is successful
     */
    protected fun <T> UIState<T>.setupViewVisibility(group: Group, loader: CircularProgressIndicator, showViewIfSuccess: Boolean = true)
    {
        fun showLoader(isVisible: Boolean)
        {
            group.isVisible = !isVisible
            loader.isVisible = isVisible
        }

        when (this)
        {
            is UIState.Idle ->
            {
            }

            is UIState.Loading -> showLoader(true)
            is UIState.Error -> showLoader(false)
            is UIState.Success -> showLoader(!showViewIfSuccess)
        }
    }

    /**
     * Extension function for setup errors from server side
     *
     * @receiver [NetworkError]
     */
    private fun NetworkError.setupApiErrors() = when (this)
    {
        is NetworkError.Timeout ->
        {
            showToastLong("Timeout")
        }

        is NetworkError.Unexpected ->
        {
            showToastLong(this.message)
            Log.e("Tag", this.message)
        }

        is NetworkError.Api ->
        {
            showToastLong(this.message)
            Log.e("Tag", this.message)
        }
    }
}