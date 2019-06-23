package uk.co.jatra.noted.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
class SchedulerModule {

    @Provides
    @Named("IOScheduler")
    fun providesIOScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Named("Main")
    fun providesMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}

