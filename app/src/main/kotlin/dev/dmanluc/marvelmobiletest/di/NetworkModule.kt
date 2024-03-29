package dev.dmanluc.marvelmobiletest.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.dmanluc.marvelmobiletest.data.remote.api.MarvelApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Koin DI module for remote network based dependencies
 *
 */
fun createNetworkModule(baseUrl: String) = module {
    single { provideGson() }
    single { provideOkHttpClient() }
    single { provideRetrofit(okHttpClient = get(), url = baseUrl) }
    single { provideMarvelApiService(retrofit = get()) }
}

private fun provideGson(): Gson = GsonBuilder().create()

private fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideMarvelApiService(retrofit: Retrofit): MarvelApi =
    retrofit.create(MarvelApi::class.java)
