package globals.communication

import globals.Storage
import org.w3c.xhr.XMLHttpRequest

object HttpClient {
    private const val ip = "localhost"
    private const val port = "8080"
    private const val path = "api"

    private fun makeUrl(url: String): String {
        return "http://$ip:$port/$path/$url"
    }

    fun setHeader(request: XMLHttpRequest) {
        with(request) {
            setRequestHeader("token", Storage.getToken())
        }
    }

    fun get(url: String, callback: (XMLHttpRequest) -> Unit) {
        val xmlHttp = XMLHttpRequest()
        with(xmlHttp) {
            open("GET", makeUrl(url),true)
            onload = {
                callback.invoke(this)
            }
            setHeader(xmlHttp)
            send()
        }
    }
    fun post(url: String, obj: Any? = null, callback: (XMLHttpRequest) -> Unit) {
        val xmlHttp = XMLHttpRequest()
        with(xmlHttp) {
            open("POST", makeUrl(url), true)
            onload = {
                callback.invoke(xmlHttp)
            }
            setHeader(this)
            when {
                obj != null -> send(JSON.stringify(obj))
                else -> send()
            }
        }
    }

}