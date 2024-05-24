package dev.hawk0f.itutor.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateAge
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateEmail
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateIsEmpty
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidateName
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidatePassword
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidatePhone
import dev.hawk0f.itutor.core.presentation.validation.usecases.ValidatePrice

@Module
@InstallIn(ViewModelComponent::class)
object PresentationModule
{
    // Validators
    @Provides
    fun provideValidateEmailUseCase(@ApplicationContext context: Context) = ValidateEmail(context)

    @Provides
    fun provideValidateIsEmptyUseCase(@ApplicationContext context: Context) = ValidateIsEmpty(context)

    @Provides
    fun provideValidateNameUseCase(@ApplicationContext context: Context) = ValidateName(context)

    @Provides
    fun provideValidatePasswordUseCase(@ApplicationContext context: Context) = ValidatePassword(context)

    @Provides
    fun provideValidatePhoneUseCase(@ApplicationContext context: Context) = ValidatePhone(context)

    @Provides
    fun provideValidateAgeUseCase(@ApplicationContext context: Context) = ValidateAge(context)

    @Provides
    fun provideValidatePriceUseCase(@ApplicationContext context: Context) = ValidatePrice(context)
}