package com.example.mydemo.main.core.shopping.di

import com.example.mydemo.di.RepositoryModule
import com.example.mydemo.main.core.shopping.repositories.FakeShoppingRepositoryAndroidTest
import com.example.mydemo.main.core.shopping.repositories.ShoppingRepository
import com.example.mydemo.repository.InterviewRepository
import com.example.mydemo.service.InterviewService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [RepositoryModule::class])
object FakeRepositoryModule {

    @Provides
    fun provideInterviewRepository(guestService: InterviewService) =
        InterviewRepository(guestService)

    @Provides
    fun provideFakeShoppingRepositoryAndroidTest() =
        FakeShoppingRepositoryAndroidTest() as ShoppingRepository
}