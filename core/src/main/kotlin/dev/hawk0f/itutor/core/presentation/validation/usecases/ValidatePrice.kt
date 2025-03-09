package dev.hawk0f.itutor.core.presentation.validation.usecases

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hawk0f.itutor.core.R
import dev.hawk0f.itutor.core.presentation.validation.ValidationResult
import dev.hawk0f.itutor.core.presentation.validation.Validator
import javax.inject.Inject

class ValidatePrice @Inject constructor(@ApplicationContext private val context: Context) : Validator
{
    override fun invoke(text: String): ValidationResult
    {
        return when
        {
            text.isEmpty() ->
            {
                ValidationResult(isSuccessful = false, errorMessage = context.getString(R.string.field_must_be_filled))
            }

            text.toInt() == 0 ->
            {
                ValidationResult(isSuccessful = false, errorMessage = context.getString(R.string.invalid_price))
            }

            else ->
            {
                ValidationResult(isSuccessful = true)
            }
        }
    }

}