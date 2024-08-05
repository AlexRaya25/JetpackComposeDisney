package com.rayadev.jetpackcomposedisney.di

import android.content.Context
import androidx.room.Room
import com.rayadev.data.database.AppDatabase
import com.rayadev.data.database.CharacterDao
import com.rayadev.data.mapper.CharacterEntityToCharacterMapper
import com.rayadev.data.repository.CharactersRepository
import com.rayadev.data.server.CharactersApi
import com.rayadev.domain.constants.Constants.BASE_URL
import com.rayadev.domain.constants.Constants.DATABASE_NAME
import com.rayadev.domain.di.ErrorInterceptor
import com.rayadev.data.mapper.CharacterEntityMapper
import com.rayadev.domain.repositories.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val headerInterceptor = okhttp3.Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (HTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .header("Accept", "application/json")
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(ErrorInterceptor())
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient): CharactersApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharactersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(appDatabase: AppDatabase): CharacterDao {
        return appDatabase.characterDao()
    }

    @Provides
    fun provideCharacterRepository(repository: CharactersRepository): CharacterRepository {
        return repository
    }

    @Provides
    @Singleton
    fun provideCharacterEntityMapper(): CharacterEntityMapper {
        return CharacterEntityToCharacterMapper()
    }

    @Provides
    @Singleton
    fun provideCharactersRepository(api: CharactersApi, characterDao: CharacterDao): CharactersRepository {
        return CharactersRepository(api, characterDao)
    }
}
