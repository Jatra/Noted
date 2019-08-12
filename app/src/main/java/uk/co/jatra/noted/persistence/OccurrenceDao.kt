package uk.co.jatra.noted.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable

import io.reactivex.Flowable
import uk.co.jatra.noted.model.Occurrence

/**
 * Data Access Object for the occurrences table.
 */
@Dao
interface OccurrenceDao {

    /**
     * Get a occurrence by id.
     * @return the occurrence from the table with a specific id.
     */
    @Query("SELECT * FROM Occurrence WHERE id = :id")
    fun getOccurrenceById(id: String): Flowable<Occurrence>

    /**
     * Get al occurrences
     * @return all occurrences
     */

    @Query("SELECT * FROM Occurrence")
    fun getAllOccurrences(): Flowable<List<Occurrence>>

    /**
     * Insert a occurrence in the database. If the occurrence already exists, replace it.
     * @param occurrence the occurrence to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOccurrence(occurrence: Occurrence): Completable

    /**
     * Delete all occurrences.
     */
    @Query("DELETE FROM Occurrence")
    fun deleteAllOccurrences()
}