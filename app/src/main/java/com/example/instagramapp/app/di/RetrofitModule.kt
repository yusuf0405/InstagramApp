package com.example.instagramapp.app.di

import com.example.instagramapp.app.network.api.PostApi
import com.example.instagramapp.app.network.api.UserApi
import com.example.instagramapp.app.utils.Cons.Companion.APPLICATION_ID
import com.example.instagramapp.app.utils.Cons.Companion.BASE_URL
import com.example.instagramapp.app.utils.Cons.Companion.REST_API_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun requestInterceptor() = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Parse-Application-Id", APPLICATION_ID)
            .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
            .build()
        return@Interceptor chain.proceed(request)
    }

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Provides
    @Singleton
    fun okHttpClient(
        requestInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun postRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun postApi(retrofit: Retrofit): PostApi =
        retrofit.create(PostApi::class.java)

    @Provides
    @Singleton
    fun userApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)


}