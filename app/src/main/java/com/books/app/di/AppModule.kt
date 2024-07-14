package com.books.app.di

import com.books.app.domain.repository.BooksRepository
import com.books.app.data.BooksRepositoryImpl
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRepository(): BooksRepository =
        BooksRepositoryImpl(provideApi())

        private fun provideApi(): FirebaseRemoteConfig {
            val instanceFbc = FirebaseRemoteConfig.getInstance()
            instanceFbc.fetchAndActivate()
            return instanceFbc
        }

}