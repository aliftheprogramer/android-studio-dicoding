package com.example.myapplication.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.myapplication.data.database.FavoriteEventDao
import com.example.myapplication.data.database.FavoriteEventRoomDatabase
import com.example.myapplication.ui.settings.SettingPreferences
import com.example.myapplication.ui.settings.dataStore
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


    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideSettingPreferences(dataStore: DataStore<Preferences>): SettingPreferences {
        return SettingPreferences(dataStore)
    }
}