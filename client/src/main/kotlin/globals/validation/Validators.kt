package globals.validation

import kotlin.js.RegExp

object Validators {
    private val email_regex = RegExp("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")
    private val phone_regex = RegExp("^[0][6]\\d{9}$|" + "^[1-9]\\d{9}$")
    private val zipcode_regex = RegExp("^\\d\\d\\d\\d$")
    private val oneCharOrMore = RegExp("^[A-Za-áéíóöőúüűÁÉÍÓÖŐÚÜŰ]+[A-Za-áéíóöőúüűÁÉÍÓÖŐÚÜŰ ]*$")
    private val streetnumber_regex = RegExp("^\\d+$")
    private val passwordRegex = RegExp("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,40}$")


    fun email(text: String): Boolean = email_regex.exec(text) != null
    fun phone(text: String): Boolean = phone_regex.exec(text) != null
    fun zipcode(text: String): Boolean = zipcode_regex.exec(text) != null
    fun oneCharOrMore(text: String): Boolean = oneCharOrMore.exec(text) != null
    fun streetnumber(text: String): Boolean = streetnumber_regex.exec(text) != null
    fun password(text: String): Boolean = passwordRegex.exec(text) != null
}