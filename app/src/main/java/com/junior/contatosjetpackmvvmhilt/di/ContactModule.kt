package com.junior.contatosjetpackmvvmhilt.di

import android.content.Context
import androidx.room.Room
import com.junior.contatosjetpackmvvmhilt.data.datasource.local.AppDataBase
import com.junior.contatosjetpackmvvmhilt.data.datasource.local.dao.ContactDao
import com.junior.contatosjetpackmvvmhilt.data.datasource.local.MIGRATION_1_2
import com.junior.contatosjetpackmvvmhilt.data.datasource.local.MIGRATION_2_3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ContactModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDataBase =
        Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "app_database"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()

    @Provides
    fun provideContactDao(db: AppDataBase): ContactDao = db.contactDao()
}
