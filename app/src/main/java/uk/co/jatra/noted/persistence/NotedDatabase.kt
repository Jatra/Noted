package uk.co.jatra.noted.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.co.jatra.noted.model.Event
import uk.co.jatra.noted.model.Occurrence
import uk.co.jatra.noted.model.OccurrenceDetail
import uk.co.jatra.noted.model.User

@Database(
    entities = [User::class, Event::class, Occurrence::class],
    views = [OccurrenceDetail::class],
    version = 3
)
abstract class NotedDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
    abstract fun occurrenceDao(): OccurrenceDao

    companion object {

        @Volatile
        private var INSTANCE: NotedDatabase? = null

        fun getInstance(context: Context): NotedDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NotedDatabase::class.java, "Noted.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}