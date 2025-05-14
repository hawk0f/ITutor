package dev.hawk0f.itutor.core.presentation.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Fast show [Toast]
 *
 * @receiver [Context]
 */
fun Context.showToastShort(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

/**
 * Fast show [Toast]
 *
 * @receiver [Context]
 */
fun Context.showToastLong(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

/**
 * Fast show [Toast]
 *
 * @receiver [Context]
 */
fun Context.showToastLong(@StringRes textFromRes: Int) {
    Toast.makeText(this, textFromRes, Toast.LENGTH_LONG).show()
}

/**
 * Fast show [Toast]
 *
 * @receiver [Context]
 */
fun Context.showToastShort(@StringRes textFromRes: Int) {
    Toast.makeText(this, textFromRes, Toast.LENGTH_SHORT).show()
}