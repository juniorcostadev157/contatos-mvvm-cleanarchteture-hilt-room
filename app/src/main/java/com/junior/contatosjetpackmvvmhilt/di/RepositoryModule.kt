package com.junior.contatosjetpackmvvmhilt.di

import com.junior.contatosjetpackmvvmhilt.data.repository.local.ContactRepositoryImpl
import com.junior.contatosjetpackmvvmhilt.domain.repository.local.ContactRepository
import com.junior.contatosjetpackmvvmhilt.domain.repository.remote.CepRepository
import com.junior.contatosjetpackmvvmhilt.data.repository.remote.CepRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindContactRepository(impl: ContactRepositoryImpl): ContactRepository

    @Binds
    @Singleton
    abstract fun bindCepRepository(impl: CepRepositoryImpl): CepRepository


}