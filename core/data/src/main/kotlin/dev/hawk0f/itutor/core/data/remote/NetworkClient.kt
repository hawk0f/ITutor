package dev.hawk0f.itutor.core.data.remote

import retrofit2.Retrofit

class NetworkClient
{
    val provideRetrofit: Retrofit = provideRetrofit(provideOkHttpClientBuilder().build())

    inline fun <reified T> provideApiService(): T = provideRetrofit.create(T::class.java)
}