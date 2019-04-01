package globals.communication

import org.w3c.xhr.XMLHttpRequest

object HttpClient {
    const val ip = "localhost"
    const val port = "8080"
    const val path = "api"

    private fun makeUrl(url: String): String {
        return "http://$ip:$port/$path/$url"
    }

    fun get(url: String, callback: (String) -> Unit) {
        val xmlHttp = XMLHttpRequest()
        xmlHttp.open("GET", makeUrl(url))
        xmlHttp.onload = {
            callback.invoke(xmlHttp.responseText)
        }
        xmlHttp.send()
    }
    fun post(url: String, obj: Any, callback: (String) -> Unit) {
        val xmlHttp = XMLHttpRequest()
        xmlHttp.open("POST", makeUrl(url))
        xmlHttp.onload = {
            callback.invoke(xmlHttp.responseText)
        }
        xmlHttp.send(JSON.stringify(obj))
    }

}