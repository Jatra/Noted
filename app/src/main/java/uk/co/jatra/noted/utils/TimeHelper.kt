package uk.co.jatra.noted.utils

import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

//TODO Use a better time apis
@Singleton
class TimeHelper @Inject constructor() {
    val now
    get() = Date().time
}