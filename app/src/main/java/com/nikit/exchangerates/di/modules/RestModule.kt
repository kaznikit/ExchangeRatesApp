package com.nikit.exchangerates.di.modules

import com.google.gson.Gson
import com.nikit.rest.api.CurrencyApi
import com.nikit.rest.calladapter.ApiCallAdapterFactory
import com.nikit.rest.calladapter.ApiResponseTransformer
import com.nikit.rest.calladapter.DefaultResponseTransformer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RestModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        transformer: ApiResponseTransformer,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ApiCallAdapterFactory.create(transformer))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun currencyApi(retrofit: Retrofit): CurrencyApi =
        retrofit.create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .apply { connectTimeout(timeout = 10, unit = TimeUnit.SECONDS) }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                }
            )
            .build()

    @Singleton
    @Provides
    fun provideTransformer(
        gson: Gson
    ): ApiResponseTransformer = DefaultResponseTransformer(
        gson = gson
    )

    companion object {
        private const val BASE_URL = "https://api.frankfurter.app/"
    }
}