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
    fun HTMLElement.span(text: String = "", op: HTMLSpanElement.() -> Unit = {}): HTMLSpanElement {
        val item = document.createElement("span") as HTMLSpanElement
        item.textContent = text
        this.appendChild(item)
        op.invoke(item)
        return item
    }

    fun HTMLElement.textfield(type: String = "", op: HTMLInputElement.() -> Unit = {}): HTMLInputElement {
        val item = document.createElement("input") as HTMLInputElement
        item.type = type
        this.appendChild(item)
        op.invoke(item)
        return item
    }

    fun HTMLElement.img(src: String, op: HTMLImageElement.() -> Unit = {}): HTMLImageElement {
        val item = document.createElement("img") as HTMLImageElement
        item.src = src
        this.appendChild(item)
        op.invoke(item)
        return item
    }

    fun div() = document.createElement("div") as HTMLDivElement
}
