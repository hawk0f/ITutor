package dev.hawk0f.itutor.core.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.hawk0f.itutor.core.data.BuildConfig
import dev.hawk0f.itutor.core.data.utils.jsonClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient).addConverterFactory(jsonClient.asConverterFactory("application/json".toMediaType())).build()

internal fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient().newBuilder().addInterceptor(provideLoggingInterceptor()).callTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)

private fun provideLoggingInterceptor() = HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)