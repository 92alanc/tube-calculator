package com.alancamargo.tubecalculator.core.network

import com.alancamargo.tubecalculator.core.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
internal class ApiProviderImpl @Inject constructor(
    private val baseUrl: String
) : ApiProvider {

    private val json = Json { ignoreUnknownKeys = true }

    override fun <T> provideService(clazz: Class<T>): T {
        val converterFactory = getConverterFactory()
        val client = getClient()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
            .create(clazz)
    }

    private fun getConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    private fun getClient(): OkHttpClient {
        val loggingInterceptor = getLoggingInterceptor()
        val appIdAndKeyInterceptor = getAppIdAndKeyInterceptor()

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(appIdAndKeyInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    private fun getLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun getAppIdAndKeyInterceptor(): (Interceptor.Chain) -> Response = { chain ->
        val newUrl = chain.request().url.newBuilder()
            .addQueryParameter("app_id", BuildConfig.APP_ID)
            .addQueryParameter("app_key", BuildConfig.APP_KEY)
            .build()

        val newRequest = chain.request().newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }
}
