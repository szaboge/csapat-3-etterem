package globals

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.security.MessageDigest


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
}