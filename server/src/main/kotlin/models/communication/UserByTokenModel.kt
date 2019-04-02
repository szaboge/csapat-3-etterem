package models.communication

import org.joda.time.DateTime

class UserByTokenModel(val userID: Int, val role: String, val expiration: DateTime)