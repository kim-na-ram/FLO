package com.naram.flo.di

import com.naram.data.FloRepositoryImpl
import com.naram.domain.repo.FloRepository
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
    abstract fun bindsFloRepository(repository: FloRepositoryImpl): FloRepository
}