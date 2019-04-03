package globals

import kotlin.browser.localStorage


object Storage {
    val tokenKey: String = "token"
    fun setToken(value: String) = localStorage.setItem(tokenKey, value)
    fun getToken(): String = localStorage.getItem(tokenKey)?:""
    fun cleanToken() = localStorage.removeItem(tokenKey)
}