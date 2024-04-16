package dev.hawk0f.itutor.core.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.hawk0f.itutor.core.data.BuildConfig
import dev.hawk0f.itutor.core.data.utils.jsonClient
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

class NetworkClient
{
    val provideRetrofit: Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(jsonClient.asConverterFactory("application/json".toMediaType())).build()

    inline fun <reified T> provideApiService(): T = provideRetrofit.create(T::class.java)
}