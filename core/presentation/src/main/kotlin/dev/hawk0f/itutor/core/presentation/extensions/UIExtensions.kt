package dev.hawk0f.itutor.core.presentation.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.progressindicator.LinearProgressIndicator

/**
 * Fast show [Toast]
 *
 * @receiver [Fragment]
 */
fun Fragment.showToastShort(text: String)
{
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.parentLoader(@IdRes loaderId: Int): LinearProgressIndicator = requireParentFragment().requireView().findViewById(loaderId)

/**
 * Fast show [Toast]
 *
 * @receiver [Fragment]
 */
fun Fragment.showToastShort(@StringRes textFromRes: Int)
{
    Toast.makeText(context, textFromRes, Toast.LENGTH_SHORT).show()
}

/**
 * Fast show [Toast]
 *
 * @receiver [Fragment]
 */
fun Fragment.showToastLong(text: String)
{
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

/**
 * Fast show [Toast]
 *
 * @receiver [Fragment]
 */
fun Fragment.showToastLong(@StringRes textFromRes: Int)
{
    Toast.makeText(context, textFromRes, Toast.LENGTH_LONG).show()
}

/**
 * Show keyboard extension function
 *
 * @receiver [View]
 */
fun View.showKeyboard()
{
    if (this.requestFocus())
    {
        val imm = this.context.getSystemService(InputMethodManager::class.java)
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

/**
 * Hide keyboard extension function
 *
 * @receiver [Fragment]
 */
fun Fragment.hideKeyboard()
{
    val inputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
}