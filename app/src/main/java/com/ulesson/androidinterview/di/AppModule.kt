package com.ulesson.androidinterview.di

import android.content.Context
import androidx.room.Room
import com.ulesson.androidinterview.BuildConfig
import com.ulesson.androidinterview.model.local.AppDatabase
import com.ulesson.androidinterview.model.local.dao.ChapterDao
import com.ulesson.androidinterview.model.local.dao.LessonDao
import com.ulesson.androidinterview.model.local.dao.SubjectDao
import com.ulesson.androidinterview.model.remote.ApiService
import com.ulesson.androidinterview.model.remote.Network
import com.ulesson.androidinterview.model.remote.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClient() : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        })
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesNetwork(@ApplicationContext ctx: Context): Network = NetworkUtil(ctx)

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext ctx: Context): AppDatabase {
        return Room.databaseBuilder(ctx, AppDatabase::class.java, "grade_db").fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesSubjectDao(database: AppDatabase): SubjectDao {
        return database.subjectDao()
    }

    @Provides
    @Singleton
    fun providesChapterDao(database: AppDatabase): ChapterDao {
        return database.chapterDao()
    }

    @Provides
    @Singleton
    fun providesLessonDao(database: AppDatabase): LessonDao {
        return database.lessonDao()
    }
}