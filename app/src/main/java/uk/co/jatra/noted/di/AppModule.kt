package uk.co.jatra.noted.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//Not actually being used at present.
//Nothing yet needs the context injected,
//classes using injection don't need specific Provider methods
@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun providesContext(): Context = app
}