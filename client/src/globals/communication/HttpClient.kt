package globals.communication

import org.w3c.xhr.XMLHttpRequest

object HttpClient {
    const val ip = "localhost"
    const val port = "7000"

    private fun makeUrl(url: String): String {
        return "http://$ip:$port/$url"
    }

    fun get(url: String, callback: (String) -> Unit) {
        val xmlHttp = XMLHttpRequest()
        xmlHttp.open("GET", makeUrl(url))
        xmlHttp.onload = {
            callback.invoke(xmlHttp.responseText)
           /* if (xmlHttp.readyState == 4.toShort() && xmlHttp.status == 200.toShort()) {
            }*/
        }
        xmlHttp.send()
    }
}