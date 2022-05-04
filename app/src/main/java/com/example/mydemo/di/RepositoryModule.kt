package com.example.mydemo.di

import com.example.mydemo.main.core.shopping.data.local.ShoppingDao
import com.example.mydemo.main.core.shopping.data.remote.PixabayAPI
import com.example.mydemo.main.core.shopping.repositories.DefaultShoppingRepository
import com.example.mydemo.main.core.shopping.repositories.ShoppingRepository
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

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository
}