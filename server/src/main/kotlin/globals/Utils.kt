package globals

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.security.MessageDigest
import java.util.regex.Pattern


fun DateTime.format(): String = Utils.fmt.print(this)

object Utils {
    val magicword: String = "tits"
    val fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

    fun sha256(input: String): String = hashString("SHA-256", input)
    private fun hashString(type: String, input: String): String {
        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }

    fun createPassword(password: String): String {
        return Utils.sha256("$password${Utils.sha256(magicword)}")
    }

    fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,40}$")
            .matcher(password).matches()
    }

    fun isNameValid(name: String): Boolean{
        return Pattern.compile("^[A-Za-áéíóöőúüűÁÉÍÓÖŐÚÜŰ]+[A-Za-áéíóöőúüűÁÉÍÓÖŐÚÜŰ ]*$")
            .matcher(name).matches()
    }

    fun isPhoneValid(phone: String): Boolean{
        return Pattern.compile("^[0][6]\\d{9}$|" + "^[1-9]\\d{9}$")
            .matcher(phone).matches()
    }

    fun isZipcodeValid(zipcode: Int): Boolean{
        return Pattern.compile("^\\d\\d\\d\\d$")
            .matcher(zipcode.toString()).matches()
    }

    fun isCityValid(city: String): Boolean{
        return Pattern.compile("^[A-Za-áéíóöőúüűÁÉÍÓÖŐÚÜŰ]+[A-Za-áéíóöőúüűÁÉÍÓÖŐÚÜŰ ]*$")
            .matcher(city).matches()
    }

    fun isStreetValid(street: String): Boolean{
        return Pattern.compile("^[A-Za-áéíóöőúüűÁÉÍÓÖŐÚÜŰ]+[A-Za-áéíóöőúüűÁÉÍÓÖŐÚÜŰ ]*$")
            .matcher(street).matches()
    }

    fun isStrnumberValid(strnumber: String): Boolean{
        return Pattern.compile("^\\d+$").matcher(strnumber).matches()
    }

    fun isPaymentValid(payment: String): Boolean{
        return if (payment == "cash") true
        else payment == "credit-card"
    }
}