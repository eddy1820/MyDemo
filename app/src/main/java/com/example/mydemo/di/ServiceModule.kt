package com.example.mydemo.di

import com.example.mydemo.service.InterviewService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideInterviewService(retrofit: Retrofit) = retrofit.create(InterviewService::class.java)
}