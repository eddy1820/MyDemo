package com.example.mydemo.di

import com.example.mydemo.repository.InterviewRepository
import com.example.mydemo.service.InterviewService
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
    fun provideInterviewRepository(guestService: InterviewService) =
        InterviewRepository(guestService)

}