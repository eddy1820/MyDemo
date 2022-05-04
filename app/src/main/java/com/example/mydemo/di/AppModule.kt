package com.example.mydemo.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.viewbinding.BuildConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mydemo.R
import com.example.mydemo.db.AppDatabase
import com.example.mydemo.base.BaseApplication
import com.example.mydemo.main.core.shopping.data.local.ShoppingDao
import com.example.mydemo.main.core.shopping.data.local.ShoppingItemDatabase
import com.example.mydemo.main.core.shopping.data.remote.PixabayAPI
import com.example.mydemo.main.core.shopping.other.Constants.BASE_URL
import com.example.mydemo.main.core.shopping.other.Constants.DATABASE_NAME
import com.example.mydemo.main.core.shopping.repositories.DefaultShoppingRepository
import com.example.mydemo.main.core.shopping.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
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
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(RedirectInterceptor(context))
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor()
                .apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
//    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCurrencyDao(db: AppDatabase) = db.getCurrencyDao()


    //------- shopping --------
    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
    //------- shopping --------
}

private fun createResponse(chain: Interceptor.Chain, request: Request, token: String): Response {
    return chain.proceed(
        request.newBuilder()
//            .header("Content-type", "application/json")
//            .header("Accept", "application/json")
//            .header("Authorization", if (TextUtils.isEmpty(token)) "" else "Bearer $token")
//            .header(
//                "Accept-Language",
//                if (Locale.getDefault().language.contains("zh")) Properties.LANGUAGE_CN else Properties.LANGUAGE_EN
//            )
//            .header("Time-Zone", TimeZone.getDefault().id)
//            .header("Time-Zone", Properties.US_EAST_TIME_ZONE)
//            .header("Referer", Properties.REFERER)
//            .header("appType", SPORT_APP_TYPE.toString())
            .method(request.method, request.body).build()
    )
}

class RedirectInterceptor(val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        // 獲取請求
//        val request: Request = chain.request()
        // 獲取響應
        val response: Response = createResponse(chain, chain.request(), "")
//        val response: Response = chain.proceed(request);

        // 在這裡判斷是不是是token失效
        // 當然，判斷條件不會這麼簡單，會有更多的判斷條件的
        when (response.code) {
            HttpURLConnection.HTTP_MOVED_PERM -> {
            }
            HttpURLConnection.HTTP_OK -> {
            }

            HttpURLConnection.HTTP_FORBIDDEN -> {
                response.close()
                // 這裡應該呼叫自己的重新整理token的介面
                // 這裡發起的請求是同步的，重新整理完成token後再增加到header中
                // 這裡丟擲的錯誤會直接回調 onError
//                String token = refreshToken();
                Timber.e("refreshToken")
                val token: String =
                    Credentials.basic("userName", "password", Charset.forName("UTF-8"))
                // 建立新的請求，並增加header
//                val retryRequest: Request = chain.request()
//                    .newBuilder()
//                    .header("Authorization", token)
//                    .build()

                // 再次發起請求
//                return chain.proceed(retryRequest)
                return createResponse(chain, chain.request(), token)
            }
        }

        return response;
    }

}