package uk.co.jatra.noted.ui.user

import uk.co.jatra.noted.model.User


//INITIAL typically should be composed of values for view fields, DERIVED from model.
data class UserViewState(val users: List<User>)
