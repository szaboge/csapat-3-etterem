package globals

import models.communication.UserByTokenModel

interface UserChangeListener {
    fun onUserChange(newValue: UserByTokenModel)
}

object UserService {
    var user: UserByTokenModel? = null
    val listeners: MutableList<UserChangeListener> = mutableListOf()

    fun setUser(newUser: UserByTokenModel) {
        user = newUser
        notify(newUser)
    }
    fun subscribe(listener: UserChangeListener) = listeners.add(listener)


    fun notify(newUser: UserByTokenModel) = listeners.forEach { it.onUserChange(newUser) }
}