package uk.co.jatra.noted

import android.app.Application
import uk.co.jatra.noted.di.ApiModule
import uk.co.jatra.noted.di.AppComponent
import uk.co.jatra.noted.di.DaggerAppComponent
import uk.co.jatra.noted.di.SchedulerModule
import java.io.FileNotFoundException
import java.io.IOException

class NotedApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        val serverUrl = readAssetFile("serverUrl")
        appComponent = initDagger(this, serverUrl)
    }

    //as yet app is not used.
    //Virtually all apps actually need a context injected.
    private fun initDagger(app: NotedApplication, serverUrl: String): AppComponent =
        //Component named DaggerX is defined by interface X, so see AppComponent
        DaggerAppComponent.builder()
            .apiModule(ApiModule(serverUrl))
            .schedulerModule(SchedulerModule())
            .build()

    @Throws(IOException::class)
    fun readAssetFile(fileName: String): String {
        try {
            assets.open(fileName).use { stream ->
                val size = stream.available()
                val buffer = ByteArray(size)
                stream.read(buffer)
                stream.close()
                return String(buffer)
            }
        } catch (exception: FileNotFoundException) {
            return "http://example.com"
        }
    }
}

