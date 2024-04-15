package dev.hawk0f.itutor.core.presentation.validation.usecase

import android.content.Context
import android.util.Patterns
import dev.hawk0f.itutor.core.presentation.R
import dev.hawk0f.itutor.core.presentation.validation.ValidationResult
import dev.hawk0f.itutor.core.presentation.validation.Validator

class ValidateEmail(private val context: Context) : Validator
{
    override operator fun invoke(text: String): ValidationResult
    {
        return when
        {
            text.isEmpty() ->
            {
                ValidationResult(isSuccessful = false, errorMessage = context.getString(R.string.field_must_be_filled))
            }

            !Patterns.EMAIL_ADDRESS.matcher(text).matches() ->
            {
                ValidationResult(isSuccessful = false, errorMessage = context.getString(R.string.invalid_email))
            }

            else ->
            {
                ValidationResult(isSuccessful = true)
            }
        }
    }
}