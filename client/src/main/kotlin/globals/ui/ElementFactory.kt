package globals.ui

import globals.ui.ElementFactory.img
import globals.validation.Validators
import org.w3c.dom.*
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.dom.removeClass


object ElementFactory {

    fun HTMLElement.label(text: String = "", op: HTMLLabelElement.() -> Unit = {}): HTMLLabelElement {
        val item = document.createElement("label") as HTMLLabelElement
        item.textContent = text
        this.appendChild(item)
        op.invoke(item)
        return item
    }

    fun HTMLElement.radiobutton(group: String, default: Boolean, op: HTMLInputElement.() -> Unit = {}): HTMLInputElement{
        val item = document.createElement("input") as HTMLInputElement
        item.type = "radio"
        item.name = group
        item.checked = default
        this.appendChild(item)
        op.invoke(item)
        return item
    }

    fun HTMLElement.div(className: String = "", op: HTMLDivElement.() -> Unit = {}): HTMLDivElement {
        val div = document.createElement("div") as HTMLDivElement
        div.addClass(className)
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

    fun HTMLElement.icon(icon: String = "", op: HTMLSpanElement.() -> Unit = {}): HTMLSpanElement {
        val item = document.createElement("span") as HTMLSpanElement
        item.addClass("mdi mdi-$icon")
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

    fun HTMLElement.input(type: String = "", op: HTMLInputElement.() -> Unit = {}): HTMLInputElement {
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

    fun HTMLElement.p(text: String = "",op: HTMLParagraphElement.() -> Unit = {}): HTMLParagraphElement {
        val item = document.createElement("p") as HTMLParagraphElement
        item.textContent = text
        this.appendChild(item)
        op.invoke(item)
        return item
    }
    fun HTMLElement.table(op: HTMLTableElement.() -> Unit = {}): HTMLTableElement {
        val item = document.createElement("table") as HTMLTableElement
        this.appendChild(item)
        op.invoke(item)
        return item
    }
    fun HTMLElement.tr(op: HTMLTableRowElement.() -> Unit = {}): HTMLTableRowElement {
        val item = document.createElement("tr") as HTMLTableRowElement
        this.appendChild(item)
        op.invoke(item)
        return item
    }
    fun HTMLElement.td(className: String = "",op: HTMLTableCellElement.() -> Unit = {}): HTMLTableCellElement {
        val item = document.createElement("td") as HTMLTableCellElement
        item.addClass(className)
        this.appendChild(item)
        op.invoke(item)
        return item
    }

    fun HTMLInputElement.validate(type: String): Boolean =
    when(type) {
        "name" -> Validators.oneCharOrMore(this.value)
        "email" -> Validators.email(this.value)
        "phone" -> Validators.phone(this.value)
        "zipcode" -> Validators.zipcode(this.value)
        "city" -> Validators.oneCharOrMore(this.value)
        "street" -> Validators.oneCharOrMore(this.value)
        "street_number" -> Validators.streetnumber(this.value)
        "password" -> Validators.password(this.value)
        else -> false
    }

    fun HTMLInputElement.validateByClass(type: String, validClass: String, invalidClass: String): Boolean {
        val valid = validate(type)
        when(valid) {
            true -> apply { addClass(validClass)
                            removeClass(invalidClass) }
            false -> apply { addClass(invalidClass)
                            removeClass(validClass) }
        }
        return valid
    }

    fun div() = document.createElement("div") as HTMLDivElement
    fun button() = document.createElement("button") as HTMLButtonElement
}