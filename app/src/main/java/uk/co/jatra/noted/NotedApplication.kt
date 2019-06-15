package uk.co.jatra.noted

import android.app.Application
import uk.co.jatra.noted.di.AppComponent
import uk.co.jatra.noted.di.DaggerAppComponent

//INITIAL Not using injection. Just here for groundwork
class NotedApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }
    private fun initDagger(app: NotedApplication): AppComponent =
        //Component named DaggerX is defined by interface X, so see AppComponent
        DaggerAppComponent.builder()
            //AppModule not yet needed.
//            .appModule(AppModule(app))
            .build()
}