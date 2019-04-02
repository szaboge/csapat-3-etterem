package globals.communication

import org.w3c.xhr.XMLHttpRequest

object HttpClient {
    private const val ip = "localhost"
    private const val port = "8080"
    private const val path = "api"

    private fun makeUrl(url: String): String {
        return "http://$ip:$port/$path/$url"
    }

    fun get(url: String, callback: (XMLHttpRequest) -> Unit) {
        val xmlHttp = XMLHttpRequest()
        xmlHttp.open("GET", makeUrl(url))
        xmlHttp.onload = {
            callback.invoke(xmlHttp)
        }
        xmlHttp.send()
    }
    fun post(url: String, obj: Any? = null, callback: (XMLHttpRequest) -> Unit) {
        val xmlHttp = XMLHttpRequest()
        xmlHttp.open("POST", makeUrl(url))
        xmlHttp.onload = {
            callback.invoke(xmlHttp)
        }

        when {
            obj != null -> xmlHttp.send(JSON.stringify(obj))
            else -> xmlHttp.send()
        }
    }

}