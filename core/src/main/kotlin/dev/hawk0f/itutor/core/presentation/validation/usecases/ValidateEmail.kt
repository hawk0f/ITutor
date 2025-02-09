package dev.hawk0f.itutor.core.presentation.validation.usecases

import android.content.Context
import android.util.Patterns
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hawk0f.itutor.core.R
import dev.hawk0f.itutor.core.presentation.validation.ValidationResult
import dev.hawk0f.itutor.core.presentation.validation.Validator
import javax.inject.Inject

class ValidateEmail @Inject constructor(@ApplicationContext private val context: Context) : Validator
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