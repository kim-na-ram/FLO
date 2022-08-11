package com.naram.flo.di

import com.naram.data.ds.FloRemoteSource
import com.naram.data.ds.FloRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsFloRemoteSource(dataSource: FloRemoteSourceImpl): FloRemoteSource
}