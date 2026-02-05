package com.example.shopmagazine.di

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shopmagazine.data.auth.AuthInterceptor
import com.example.shopmagazine.data.local.ShopDatabase
import com.example.shopmagazine.data.local.dao.CartDao
import com.example.shopmagazine.data.local.dao.ProductDao
import com.example.shopmagazine.data.network.AuthApi
import com.example.shopmagazine.data.network.ProductsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://fakestoreapi.com/"
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(
        interceptor: AuthInterceptor
    ): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }
    @Provides
    @Singleton
    fun provideDatabase(app: Application): ShopDatabase{
        return Room.databaseBuilder(
            app,
            ShopDatabase::class.java,
            "shop_databse"
        ).build()
    }
    @Provides
    @Singleton
    fun provideCartDao(db: ShopDatabase): CartDao {
        return db.cartDao()
    }
    @Provides
    @Singleton
    fun provideProductsDao(db: ShopDatabase): ProductDao {
        return db.productDao()
    }
    @Provides
    @Singleton
    fun provideAuthApi(
        client: OkHttpClient
    ): AuthApi{
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
    @Provides
    @Singleton
    fun provideProductsApi(): ProductsApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ProductsApi::class.java)
    }


}