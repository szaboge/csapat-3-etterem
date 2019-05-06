package globals

import models.communication.UserByTokenModel
import models.communication.UserInfosModel

interface UserChangeListener {
    fun onUserChange(newValue: UserByTokenModel)
}

object UserService {
    var user: UserByTokenModel = UserByTokenModel(-1, "","UNAUTHORIZED", "")
    val listeners: MutableList<UserChangeListener> = mutableListOf()
    var token: String = ""
    var userInfo: UserInfosModel? = null

    fun setUser(newUser: UserByTokenModel) {
        user = newUser
        token = newUser.sessionID
        Storage.setToken(newUser.sessionID)
        notify(newUser)
    }
    fun subscribe(listener: UserChangeListener) = listeners.add(listener)
    fun logout() {
        setUser(UserByTokenModel(-1, "","UNAUTHORIZED", ""))
        userInfo = null
    }

    private fun notify(newUser: UserByTokenModel) = listeners.forEach { it.onUserChange(newUser) }
}