package globals.ui

import org.w3c.dom.*
import kotlin.browser.document


object ElementFactory {
    fun Button(name: String): HTMLButtonElement {
        val button = document.createElement("button") as HTMLButtonElement
        button.innerText = name
        return button
    }

    fun HTMLElement.label(text: String = "", op: HTMLLabelElement.() -> Unit = {}): HTMLLabelElement {
        val item = document.createElement("label") as HTMLLabelElement
        item.textContent = text
        this.appendChild(item)
        op.invoke(item)
        return item
    }

    fun HTMLElement.div(op: HTMLDivElement.() -> Unit = {}): HTMLDivElement {
        val div = document.createElement("div") as HTMLDivElement
        this.appendChild(div)
        op.invoke(div)
        return div
    }

    fun HTMLElement.button(text: String = "", op: HTMLButtonElement.() -> Unit = {}): HTMLButtonElement {
        val button = document.createElement("button") as HTMLButtonElement
        button.textContent = text
        this.appendChild(button)
        op.invoke(button)
        return button
    }
}
