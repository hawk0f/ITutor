package dev.hawk0f.itutor.core.presentation.validation.usecases

import android.content.Context
import dev.hawk0f.itutor.core.presentation.R
import dev.hawk0f.itutor.core.presentation.validation.ValidationResult

// TODO: implement Validator
class ValidatePasswordConfirm(private val context: Context)
{
    operator fun invoke(password: String, confirmPassword: String): ValidationResult
    {
        return when
        {
            password.isEmpty() && confirmPassword.isEmpty() ->
            {
                ValidationResult(isSuccessful = false, errorMessage = context.getString(R.string.field_must_be_filled))
            }

            password != confirmPassword ->
            {
                ValidationResult(isSuccessful = false, errorMessage = context.getString(R.string.password_do_not_match), isToast = true)
            }

            else ->
            {
                ValidationResult(isSuccessful = true)
            }
        }
    }
}