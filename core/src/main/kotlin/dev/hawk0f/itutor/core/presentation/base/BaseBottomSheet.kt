package dev.hawk0f.itutor.core.presentation.base

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.progressindicator.CircularProgressIndicator
import dev.hawk0f.itutor.core.R
import dev.hawk0f.itutor.core.domain.NetworkError
import dev.hawk0f.itutor.core.presentation.UIState
import dev.hawk0f.itutor.core.presentation.extensions.launchAndCollectIn
import dev.hawk0f.itutor.core.presentation.extensions.showToastLong
import kotlinx.coroutines.flow.StateFlow

private const val COLLAPSED_HEIGHT = 700

abstract class BaseBottomSheet<ViewModel : BaseViewModel, Binding : ViewBinding>(@LayoutRes private val layoutId: Int) : BottomSheetDialogFragment()
{
    protected abstract val viewModel: ViewModel
    protected abstract val binding: Binding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.isDraggable(false)
        //dialog.setOnShowListener { setupBottomSheetBackgroundTransparent(it) }
        return dialog
    }

    private fun setupBottomSheetBackgroundTransparent(dialogInterface: DialogInterface)
    {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) ?: return
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun Dialog.isDraggable(isDraggable: Boolean)
    {
        setCanceledOnTouchOutside(isDraggable)
        setOnShowListener { setupDraggable(it, isDraggable) }
        setOnKeyListener { _, keyCode, _ ->
            keyCode == KeyEvent.KEYCODE_BACK && isDraggable
        }
    }

    private fun setupDraggable(dialogInterface: DialogInterface, isDraggable: Boolean)
    {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) ?: return
        //bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        behavior.isDraggable = isDraggable
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(layoutId, container, false)
    }

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

    override fun onStart()
    {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density

        dialog?.let {
            // Находим сам bottomSheet и достаём из него Behaviour
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)

            // Выставляем высоту для состояния collapsed и выставляем состояние collapsed
            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
        }
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
        is NetworkError.Api ->
        {
            showToastLong(resName)
        }

        else ->
        {
            showToastLong(R.string.server_connection_error)
        }
    }
}