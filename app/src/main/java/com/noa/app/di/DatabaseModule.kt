package com.noa.app.di

import android.content.Context
import androidx.room.Room
import com.noa.app.data.database.NoADatabase
import com.noa.app.data.database.UserHabitDao
import com.noa.app.data.repository.HabitRepositoryImpl
import com.noa.app.domain.repository.UserHabitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.noa.app.data.datastore.UserPreferencesRepository

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NoADatabase {

        return Room.databaseBuilder(
            context,
            NoADatabase::class.java,
            "noa_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideHabitDao(
        database: NoADatabase
    ): UserHabitDao {

        return database.userHabitDao()
    }

    @Provides
    @Singleton
    fun provideUserHabitRepository(
        dao: UserHabitDao
    ): UserHabitRepository {

        return HabitRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(
        @ApplicationContext context: Context
    ): UserPreferencesRepository {

        return UserPreferencesRepository(context)

    }
}