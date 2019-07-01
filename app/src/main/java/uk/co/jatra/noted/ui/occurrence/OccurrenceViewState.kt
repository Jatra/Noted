package uk.co.jatra.noted.ui.occurrence

import uk.co.jatra.noted.network.Occurrence

//INITIAL typically should be composed of values for view fields, DERIVED from model.
data class OccurrenceViewState(val occurrences: List<Occurrence>)
