package dev.hawk0f.itutor.core.presentation.validation.usecases

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hawk0f.itutor.core.presentation.R
import dev.hawk0f.itutor.core.presentation.validation.ValidationResult
import dev.hawk0f.itutor.core.presentation.validation.Validator
import javax.inject.Inject

class ValidatePassword @Inject constructor(@ApplicationContext private val context: Context) : Validator
{
    override operator fun invoke(text: String): ValidationResult
    {
        return when
        {
            text.isEmpty() ->
            {
                ValidationResult(
                    isSuccessful = false,
                    errorMessage = context.getString(R.string.field_must_be_filled),
                )
            }

            text.length < 6 ->
            {
                ValidationResult(isSuccessful = false, errorMessage = context.getString(R.string.password_must_not_be_less_than_6_characters))
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