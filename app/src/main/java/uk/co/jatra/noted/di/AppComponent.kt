package uk.co.jatra.noted.di

import android.app.Application
import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import uk.co.jatra.noted.NotedActivity
import javax.inject.Singleton

//Initial Not being used yet.
//Here to lay the foundations
//TODO USE for injecting api, repositories
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(target: NotedActivity)
}

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun providesContext(): Context = app
}
