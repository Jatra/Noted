package uk.co.jatra.noted.di

import android.content.Context
import dagger.Module
import dagger.Provides
import uk.co.jatra.noted.persistence.EventDao
import uk.co.jatra.noted.persistence.NotedDatabase
import uk.co.jatra.noted.persistence.OccurrenceDao
import uk.co.jatra.noted.persistence.UserDao
import javax.inject.Singleton

@Module
class PersistenceModule {
    @Provides
    @Singleton
    fun provideUserDataSource(context: Context): UserDao {
        val database = NotedDatabase.getInstance(context)
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideEventDataSource(context: Context): EventDao {
        val database = NotedDatabase.getInstance(context)
        return database.eventDao()
    }

    @Provides
    @Singleton
    fun provideOccurrenceDataSource(context: Context): OccurrenceDao {
        val database = NotedDatabase.getInstance(context)
        return database.occurrenceDao()
    }
}