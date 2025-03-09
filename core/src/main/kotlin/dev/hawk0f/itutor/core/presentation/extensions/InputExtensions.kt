package dev.hawk0f.itutor.core.presentation.extensions

import android.util.Patterns
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dev.hawk0f.itutor.core.R
import dev.hawk0f.itutor.core.presentation.validation.ValidationResult
import dev.hawk0f.itutor.core.presentation.validation.Validator

/**
 * Get text with [toString] & [trim] from Input
 */
val EditText.fullText: String get() = this.text.toString().trim()

/**
 * Cut the text to the [limit] amount of symbols and add ... if the length is more than limit
 */
fun String.takeDot(limit: Int) = if (this.length <= limit) this else this.take(limit) + "..."

/**
 * @receiver [ViewBinding]
 * @see [getChildInputLayouts]
 */
val ViewBinding.screenInputs get() = (this.root as ViewGroup).getChildInputLayouts()

/**
 * @receiver [ViewGroup]
 * @return [List] with [TextInputLayout] in fragments xml
 */
private fun ViewGroup.getChildInputLayouts(): List<TextInputLayout>
{
    val inputs = mutableListOf<TextInputLayout>()
    for (i in 0 until childCount)
    {
        val childView = getChildAt(i)
        if (childView is TextInputLayout)
        {
            inputs.add(childView)
        }
        val childViewGroup = childView as? ViewGroup
        if (childViewGroup !is TextInputLayout)
        {
            childViewGroup?.getChildInputLayouts()
        }
    }
    return inputs
}

/**
 * Validates input fields within a Fragment using provided validators.
 *
 * &nbsp
 *
 * ## How to use:
 * ```
 * buttonSignIn.setOnClickListener {
 *     validateInputs(
 *         Pair(viewModel.validateEmail, inputLayoutSignInEmail),
 *         Pair(viewModel.validatePassword, inputLayoutSignInPassword)
 *     ) {
 *         viewModel.signIn(
 *             inputEditSignInEmail.fullText,
 *             inputEditSignInPassword.fullText
 *         )
 *     }
 * }
 * ```
 *
 * @param validatorWithInput a vararg list of pairs, each containing a Validator and a TextInputLayout.
 * @param successful a function to be executed when validation succeeds.
 */
fun Fragment.validateInputs(vararg validatorWithInput: Pair<Validator, TextInputLayout>, successful: () -> Unit)
{
    // Perform validation for each validator-input pair
    val validationResultsPair = validatorWithInput.map {
        val validator = it.first
        val inputLayout = it.second
        val inputEdit = (inputLayout.editText as EditText)

        Pair(validator.invoke(inputEdit.fullText), inputLayout)
    }

    // Extract validation results
    val validationResults = validationResultsPair.map {
        it.first
    }.toTypedArray()

    // Check if any validation result indicates error
    if (hasError(*validationResults))
    {
        // Handle error for each input layout
        validationResultsPair.map {
            val validationResult = it.first
            val inputLayout = it.second
            if (validationResult.isToast)
            {
                showToastShort(validationResult.errorMessage)
            }
            inputLayout.error = validationResult.errorMessage
        }
    }
    else
    {
        // If no error, clear errors and execute success function
        validationResultsPair.map {
            it.second.error = null
        }
        hideKeyboard()
        successful()
    }
}

/**
 * Checks if any ValidationResult indicates an error.
 *
 * @param validationResults a vararg list of ValidationResult objects.
 * @return true if any ValidationResult indicates an error, false otherwise.
 */
private fun hasError(vararg validationResults: ValidationResult) = validationResults.any {
    !it.isSuccessful
}

fun TextInputLayout.setupIsEmptyValidator() = with(editText as EditText) {
    setOnFocusChangeListener { _, hasFocus ->
        when
        {
            !hasFocus && fullText.isEmpty() ->
            {
                this@setupIsEmptyValidator.error = context.getString(R.string.field_must_be_filled)
            }

            else ->
            {
                this@setupIsEmptyValidator.error = null
            }
        }
    }
}

fun TextInputLayout.setupAgeValidator() = with(editText as EditText) {
    setOnFocusChangeListener { _, hasFocus ->
        when
        {
            !hasFocus && fullText.isEmpty() ->
            {
                this@setupAgeValidator.error = context.getString(R.string.field_must_be_filled)
            }

            !hasFocus && fullText.toIntOrNull() != null ->
            {
                if (fullText.toInt() <= 4 || fullText.toInt() >= 100)
                {
                    this@setupAgeValidator.error = context.getString(R.string.invalid_age)
                }
            }

            else ->
            {
                this@setupAgeValidator.error = null
            }
        }
    }
}

fun TextInputLayout.setupPriceValidator() = with(editText as EditText) {
    setOnFocusChangeListener { _, hasFocus ->
        when
        {
            !hasFocus && fullText.isEmpty() ->
            {
                this@setupPriceValidator.error = context.getString(R.string.field_must_be_filled)
            }

            !hasFocus && fullText.toIntOrNull() != null ->
            {
                if (fullText.toInt() == 0)
                {
                    this@setupPriceValidator.error = context.getString(R.string.invalid_price)
                }
            }

            else ->
            {
                this@setupPriceValidator.error = null
            }
        }
    }
}

fun TextInputLayout.setupEmailValidator() = with(editText as TextInputEditText) {
    setOnFocusChangeListener { _, hasFocus ->
        when
        {
            !hasFocus && fullText.isEmpty() ->
            {
                this@setupEmailValidator.error = context.getString(R.string.field_must_be_filled)
            }

            !hasFocus && !Patterns.EMAIL_ADDRESS.matcher(fullText).matches() ->
            {
                this@setupEmailValidator.error = context.getString(R.string.invalid_email)
            }

            else ->
            {
                this@setupEmailValidator.error = null
            }
        }
    }
}

fun TextInputLayout.setupNameValidator() = with(editText as TextInputEditText) {
    setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) with(fullText) {
            when
            {
                this.isEmpty() ->
                {
                    this@setupNameValidator.error = context.getString(R.string.field_must_be_filled)
                }

                !this.matches(Regex("^[\\p{L} ]+$")) ->
                {
                    this@setupNameValidator.error = context.getString(R.string.incorrect_name)
                }

                this.length < 2 ->
                {
                    this@setupNameValidator.error = context.getString(R.string.name_must_contain_at_least_2_characters)
                }
            }
        }
        else
        {
            this@setupNameValidator.error = null
        }
    }
}

fun TextInputLayout.setupPhoneValidator() = with(editText as TextInputEditText) {
    setOnFocusChangeListener { _, hasFocus ->
        when
        {
            !hasFocus && fullText.isEmpty() ->
            {
                this@setupPhoneValidator.error = context.getString(R.string.field_must_be_filled)
            }

            !hasFocus && fullText.length < 16 ->
            {
                this@setupPhoneValidator.error = context.getString(R.string.complete_your_phone_number)
            }

            else ->
            {
                this@setupPhoneValidator.error = null
            }
        }
    }
}