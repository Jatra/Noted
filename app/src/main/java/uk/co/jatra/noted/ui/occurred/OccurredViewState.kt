package uk.co.jatra.noted.ui.occurred

import uk.co.jatra.noted.network.Occurrence

//INITIAL typically should be composed of values for view fields, DERIVED from model.
data class OccurredViewState(val occurrences: List<Occurrence>)
