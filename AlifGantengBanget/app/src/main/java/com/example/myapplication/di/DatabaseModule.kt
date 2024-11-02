package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.database.FavoriteEventDao
import com.example.myapplication.data.database.FavoriteEventRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFavoriteEventRoomDatabase(context: Context): FavoriteEventRoomDatabase {
        return FavoriteEventRoomDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFavoriteEventDao(database: FavoriteEventRoomDatabase): FavoriteEventDao {
        return database.favoriteEventDao()
    }
}