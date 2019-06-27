package uk.co.jatra.noted.ui.event

import uk.co.jatra.noted.network.User


//INITIAL typically should be composed of values for view fields, DERIVED from model.
data class UserViewState(val events: List<User>)
