package dev.hawk0f.itutor.core.presentation.validation.usecases

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hawk0f.itutor.core.presentation.R
import dev.hawk0f.itutor.core.presentation.validation.ValidationResult
import dev.hawk0f.itutor.core.presentation.validation.Validator
import javax.inject.Inject

class ValidateIsEmpty @Inject constructor(@ApplicationContext private val context: Context) : Validator
{

    override operator fun invoke(text: String): ValidationResult
    {
        return when
        {
            text.isEmpty() ->
            {
                ValidationResult(isSuccessful = false, errorMessage = context.getString(R.string.field_must_be_filled))
            }

            else ->
            {
                ValidationResult(isSuccessful = true)
            }
        }
    }
}