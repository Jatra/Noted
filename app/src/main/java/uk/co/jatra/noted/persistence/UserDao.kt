package uk.co.jatra.noted.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable

import io.reactivex.Flowable
import uk.co.jatra.noted.model.User

/**
 * Data Access Object for the users table.
 */
@Dao
interface UserDao {

    /**
     * Get a user by id.

     * @return the user from the table with a specific id.
     */
    @Query("SELECT * FROM User WHERE id = :id")
    fun getUserById(id: Long): Flowable<User>

    @Query("SELECT * FROM User")
    fun getAllUsers(): Flowable<List<User>>

    /**
     * Insert a user in the database. If the user already exists, replace it.

     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Completable

    /**
     * Delete all users.
     */
    @Query("DELETE FROM User")
    fun deleteAllUsers()
}