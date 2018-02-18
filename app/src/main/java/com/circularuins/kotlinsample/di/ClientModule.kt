package com.circularuins.kotlinsample.di

import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import com.circularuins.kotlinsample.domain.repository.SchedulerProvider
import com.circularuins.kotlinsample.infra.repository.AppSchedulerProvider
import com.circularuins.kotlinsample.infra.repository.ArticlesRepositoryImpl
import com.circularuins.kotlinsample.infra.rest.QiitaClient
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by circularuins on 2018/01/21.
 */
@Module
class ClientModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
            .baseUrl("https://qiita.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideQiitaClient(retrofit: Retrofit): QiitaClient =
            retrofit.create(QiitaClient::class.java)

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    fun provideArticlesRepository(client: QiitaClient,
                                  schedulerProvider: SchedulerProvider): ArticlesRepository =
            ArticlesRepositoryImpl(client, schedulerProvider.io(), schedulerProvider.ui())
}