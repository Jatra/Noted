package uk.co.jatra.noted.ui.event

import uk.co.jatra.noted.model.Event


//INITIAL typically should be composed of values for view fields, DERIVED from model.
data class EventViewState(val events: List<Event>)
