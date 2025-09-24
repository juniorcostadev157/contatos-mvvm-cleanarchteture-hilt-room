package com.junior.contatosjetpackmvvmhilt.di

import com.junior.contatosjetpackmvvmhilt.core.contants.Constants
import com.junior.contatosjetpackmvvmhilt.data.datasource.remote.CepApiService
import com.junior.contatosjetpackmvvmhilt.domain.repository.remote.CepRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Provides
    @Singleton
    fun providerRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.ApiUrl.BASE_URL_VIA_CEP)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun provideCepApi(retrofit: Retrofit): CepApiService = retrofit.create(CepApiService::class.java)





}