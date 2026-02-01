package com.jshu.akarshit.di

import com.jshu.akarshit.api.ApiService
import com.jshu.akarshit.api.AppDao
import com.jshu.akarshit.repository.AppListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAppListRepository(
        api: ApiService,
        appDao: AppDao
    ): AppListRepository {
        return AppListRepository(api,appDao)
    }
}