package globals.ui

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.browser.document

object Presenter {
    private val root: HTMLDivElement = document.getElementById("root") as HTMLDivElement
    var previous: HTMLElement? = null

    fun present(content: HTMLElement) {
        if (previous != null) root.removeChild(previous!!)
        previous = content
        root.appendChild(content)
    }
}