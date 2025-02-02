package globals

import models.communication.UserByTokenModel

interface UserChangeListener {
    fun onUserChange(newValue: UserByTokenModel)
}

object UserService {
    var user: UserByTokenModel? = null
    val listeners: MutableList<UserChangeListener> = mutableListOf()
    var token: String = ""

    fun setUser(newUser: UserByTokenModel) {
        user = newUser
        token = newUser.sessionID
        Storage.setToken(newUser.sessionID)
        notify(newUser)
    }
    fun subscribe(listener: UserChangeListener) = listeners.add(listener)
    fun logout() {
        setUser(UserByTokenModel(-1, "UNAUTHORIZED", ""))
    }

    fun notify(newUser: UserByTokenModel) = listeners.forEach { it.onUserChange(newUser) }
}