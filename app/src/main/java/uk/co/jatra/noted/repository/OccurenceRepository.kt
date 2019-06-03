package uk.co.jatra.noted.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import uk.co.jatra.noted.network.Occurrence
import uk.co.jatra.noted.network.api
//INITIAL Just fetch one set of data from api.
object OccurenceRepository {
    //TO_DO add caching
    fun getData(query: String): Single<List<Occurrence>> {
        return api.getOccurences(query)
            .subscribeOn(Schedulers.io())
    }
}