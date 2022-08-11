package com.naram.flo.di

import com.naram.domain.FloRepositoriesUseCase
import com.naram.domain.repo.FloRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun providesFloRepositoriesUseCase(repository: FloRepository): FloRepositoriesUseCase {
        return FloRepositoriesUseCase(repository)
    }
}