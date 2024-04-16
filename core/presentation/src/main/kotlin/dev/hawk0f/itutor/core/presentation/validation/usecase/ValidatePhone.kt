package dev.hawk0f.itutor.core.presentation.validation.usecase

import android.content.Context
import dev.hawk0f.itutor.core.presentation.R
import dev.hawk0f.itutor.core.presentation.validation.ValidationResult
import dev.hawk0f.itutor.core.presentation.validation.Validator

class ValidatePhone(private val context: Context) : Validator
{

    override operator fun invoke(text: String): ValidationResult
    {
        return when
        {
            text.isEmpty() ->
            {
                ValidationResult(isSuccessful = false, context.getString(R.string.field_must_be_filled))
            }

            text.length < 18 ->
            {
                ValidationResult(isSuccessful = false, context.getString(R.string.complete_your_phone_number))
            }

            else ->
            {
                ValidationResult(
                    isSuccessful = true,
                )
            }
        }
    }
}