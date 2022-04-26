package com.example.mydemo.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.example.mydemo.service.InterviewService
import com.example.mydemo.db.AppDatabase
import com.example.mydemo.db.CurrencyDao
import com.example.mydemo.base.BaseApplication
import com.example.mydemo.repository.CurrencyRepository
import com.example.mydemo.repository.InterviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Singleton
  @Provides
  fun provideApplication(@ApplicationContext app: Context): BaseApplication {
    return app as BaseApplication
  }

  @Provides
  fun provideBaseUrl() = "https://static.mixerbox.com"

  @Singleton
  @Provides
  fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .build()
  } else {
    OkHttpClient
      .Builder()
      .build()
  }

  @Singleton
  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()


  @Singleton
  @Provides
  fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

  @Singleton
  @Provides
  fun provideCurrencyDao(db: AppDatabase) = db.getCurrencyDao()

  @Singleton
  @Provides
  fun provideCurrencyRepository(currencyDao: CurrencyDao) = CurrencyRepository(currencyDao)

  @Provides
  @Singleton
  fun provideInterviewService(retrofit: Retrofit) = retrofit.create(InterviewService::class.java)

  @Provides
  @Singleton
  fun provideInterviewRepository(guestService: InterviewService) = InterviewRepository(guestService)

}