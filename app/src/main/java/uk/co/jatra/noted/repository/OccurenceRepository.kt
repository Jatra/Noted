package uk.co.jatra.noted.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import uk.co.jatra.noted.network.Occurrence
import uk.co.jatra.noted.network.RestAdapter
import javax.inject.Inject

//INITIAL Just fetch one set of data from api.
class OccurenceRepository @Inject constructor(private val restAdapter: RestAdapter) {
    //TO_DO add caching
    fun getData(query: String): Single<List<Occurrence>> {
        return restAdapter.api.getOccurences(query)
            .subscribeOn(Schedulers.io())
    }
}