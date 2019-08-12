package uk.co.jatra.noted.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import uk.co.jatra.noted.model.User
import uk.co.jatra.noted.persistence.UserDao

class UserRepository(private val userDao: UserDao, val ioScheduler: Scheduler) {
    fun getAllUsers(): Flowable<List<User>> {
        return userDao
            .getAllUsers()
            .subscribeOn(ioScheduler)
    }

    fun addUser(user: User): Completable {
        return userDao
            .insertUser(user)
            .subscribeOn(ioScheduler)
    }
}