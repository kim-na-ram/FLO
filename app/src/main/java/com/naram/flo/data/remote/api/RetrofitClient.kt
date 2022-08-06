package com.naram.flo.data.remote.api

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitClient {
    private var instance: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return httpLoggingInterceptor
    }

    private fun getInstance(): Retrofit {
        if(instance == null) {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor())
                .build()

            instance = Retrofit.Builder()
                .baseUrl("https://grepp-programmers-challenges.s3.ap-northeast-2.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
        }
        return instance!!
    }

    @Provides
    @Singleton
    fun create(): ApiService {
        return getInstance().create(ApiService::class.java)
    }

}