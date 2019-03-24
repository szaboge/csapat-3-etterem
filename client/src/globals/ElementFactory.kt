package globals

import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLLabelElement
import kotlin.browser.document

object ElementFactory {
    fun Button(name: String): HTMLButtonElement {
        val button = document.createElement("button") as HTMLButtonElement
        button.innerText = name
        return button
    }

    fun Label(name: String): HTMLLabelElement {
        val label = document.createElement("label") as HTMLLabelElement
        label.textContent = name
        return label
    }

    fun Div(id: String): HTMLDivElement {
        val div = document.createElement("div") as HTMLDivElement
        div.id = id
        return div
    }

}