package globals.ui

import org.w3c.dom.*
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.js.RegExp


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

    fun HTMLElement.radiobutton(group: String, default: Boolean, op: HTMLInputElement.() -> Unit = {}): HTMLInputElement{
        val item = document.createElement("input") as HTMLInputElement
        item.type = "radio"
        item.name = group
        item.checked = default
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

    fun HTMLElement.img(src: String, op: HTMLImageElement.() -> Unit = {}): HTMLImageElement {
        val item = document.createElement("img") as HTMLImageElement
        item.src = src
        this.appendChild(item)
        op.invoke(item)
        return item
    }

    fun HTMLInputElement.validate(type: String): Boolean {
        var valid = true

        val name_regex = RegExp("\\w+")

        val email_regex = RegExp("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")

        val phone_regex = RegExp("^[0][6]\\d{9}$|" + "^[1-9]\\d{9}$")

        val zipcode_regex = RegExp("^\\d\\d\\d\\d$")

        val city_regex = RegExp("^[A-Za-áéíóöőúüűÁÉÍÓÖŐÚÜŰ]+$")

        val street_regex = RegExp("^[A-Za-áéíóöőúüűÁÉÍÓÖŐÚÜŰ]+$")

        val streetnumber_regex = RegExp("^\\d+$")

        when(type){
            "name" -> valid = name_regex.exec(this.value) != null
            "email" -> valid = email_regex.exec(this.value) != null
            "phone" -> valid = phone_regex.exec(this.value) != null
            "zipcode" -> valid = zipcode_regex.exec(this.value) != null
            "city" -> valid = city_regex.exec(this.value) != null
            "street" -> valid = street_regex.exec(this.value) != null
            "street_number" -> valid = streetnumber_regex.exec(this.value) != null
        }

        return valid
    }

    fun div() = document.createElement("div") as HTMLDivElement
}