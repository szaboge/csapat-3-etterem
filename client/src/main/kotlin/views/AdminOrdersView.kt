package views

import abstracts.View
import globals.Enums
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.icon
import globals.ui.ElementFactory.img
import globals.ui.ElementFactory.label
import globals.ui.ElementFactory.span
import globals.ui.ElementFactory.table
import globals.ui.ElementFactory.td
import globals.ui.ElementFactory.tr
import globals.ui.Routes
import models.communication.GetOrderModel
import org.w3c.dom.*
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.addClass

class AdminOrdersView : View() {
    override val routeType: Routes = Routes.ADMINORDERS
    lateinit var destination: HTMLDivElement

    override fun render() {
        with(root) {
            addClass("myorders-root")
            div {
                addClass("myorders-container")
                label("Aktív rendelések") {
                    addClass("title")
                }
                destination = div {
                    addClass("myorders-accordions-container")
                }
            }
        }
    }

    fun initialize() {
        val acc = document.getElementsByClassName("myorders-accordion")
        for (i in 0 until acc.length) {
            with(acc[i]!!) {
                val panel = nextElementSibling as HTMLDivElement
                panel.style.maxHeight = "0px"
                addEventListener("click", {
                    (acc[i]!!.getElementsByClassName("myorders-chevron-container")[0] as HTMLDivElement)
                        .classList.toggle("myorders-chevron-up")
                    classList.toggle("active")
                    if (panel.style.maxHeight != "0px") {
                        panel.style.maxHeight = "0px"
                    } else {
                        panel.style.maxHeight = "${panel.scrollHeight + 20}px"
                    }
                })
            }
        }
    }

    fun createAccordion(parent: HTMLElement, element: GetOrderModel) {
        lateinit var titleState: HTMLSpanElement
        with(parent) {
            button {
                addClass("myorders-accordion")
                div {
                    addClass("myorders-accordion-title")
                    div {
                        span(element.orderID.toString() + " ")
                        span(element.name + " - " + element.restaurantName)
                    }
                    titleState = span(Enums.Statuses.valueOf(element.status).value())
                }
                div {
                    addClass("myorders-chevron-container")
                    icon("chevron-down") {
                        addClass("myorders-chevron")
                    }
                }
            }
            div {
                addClass("myorders-panel")
                div {
                    addClass("myorders-panel-container")
                    val progress = div {
                        addClass("myorders-panel-title")
                        createProgress(element.status, this)
                    }
                    div {
                        addClass("myorders-panel-info")
                        div {
                            addClass("myorders-info")
                            div {
                                addClass("myorders-info-title")
                                label("Szállítási cím")
                                icon("truck") {
                                    addClass("myorders-info-icon")
                                }
                            }
                            div {
                                addClass("myorders-info-address")
                                span(element.zipcode.toString())
                                span(element.city)
                                span(element.street)
                                span(element.strnumber)
                            }
                        }
                        div {
                            addClass("myorders-info")
                            div {
                                addClass("myorders-info-title")
                                label("Rendelés információk")
                                icon("package-variant-closed") {
                                    addClass("myorders-info-icon")
                                }
                            }
                            div {
                                addClass("myorders-info-address")
                                span(element.name)
                                span(element.email)
                                span(element.payment)
                                span(element.phone)
                                span(element.date)
                            }
                        }
                        div {
                            addClass("myorders-info")
                            div {
                                addClass("myorders-info-title")
                                label("Ételek")
                                icon("food") {
                                    addClass("myorders-info-icon")
                                }
                            }
                            div {
                                addClass("myorders-info-address")
                                table {
                                    addClass("myorders-foods-table")
                                    element.foods.forEach { food ->
                                        tr {
                                            td {
                                                addClass("myorders-foods-item-name")
                                                span(food.name)
                                            }
                                            td {
                                                addClass("myorders-foods-item")
                                                span("${food.count} db")
                                            }
                                            td {
                                                addClass("myorders-foods-item")
                                                span("${food.price} Ft")
                                            }
                                        }
                                    }
                                    tr {
                                        addClass("myorders-foods-container")
                                        td {
                                            addClass("myorders-foods-item-name")
                                            span("Összesen:")
                                        }
                                        td {
                                            addClass("myorders-foods-item")
                                        }
                                        td {
                                            addClass("myorders-foods-item")
                                            span("${element.amount} Ft")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    div {
                        addClass("riderorders-footer")
                        button("FELDOLGOZÁS ALATT") {
                            addClass("default-button riderorders-in-shipping")
                            addEventListener("click", {
                                modifyState(Enums.Statuses.ARRIVING, progress, titleState, element.orderID)
                            })
                        }
                        button("KÉSZÍTÉS ALATT") {
                            addClass("default-button riderorders-in-shipping")
                            addEventListener("click", {
                                modifyState(Enums.Statuses.MAKING, progress, titleState, element.orderID)
                            })
                        }
                        button("SZÁLLÍTÁS ALATT") {
                            addClass("default-button riderorders-in-shipping")
                            addEventListener("click", {
                                modifyState(Enums.Statuses.SHIPPING, progress, titleState, element.orderID)
                            })
                        }
                        button("KISZÁLLÍTVA") {
                            addClass("default-button riderorders-in-shipping")
                            addEventListener("click", {
                                modifyState(Enums.Statuses.DONE, progress, titleState, element.orderID)
                            })
                        }
                        button("TÖRLÉS") {
                            addClass("default-button")
                            addEventListener("click", {
                                if (!window.confirm("Biztos hogy kitörlöd?")) return@addEventListener
                                (it.target as HTMLButtonElement).disabled = true
                                ApiService.deleteOrder(
                                    object {
                                        val orderID = element.orderID
                                }) {
                                    getOrders()
                                }
                            })
                        }
                    }
                }
            }
        }
    }

    fun createProgress(status: String, root: HTMLDivElement) {
        var flag = false
        var first = true
        val div = root.div()
        with(div) {
            addClass("progress-container")
            Enums.Statuses.values().forEach {
                if (status == Enums.Statuses.DONE.name && it.name == status) {
                    createProgressItem(this, it.value(), "DONE", first)
                } else if (it.name == status) {
                    createProgressItem(this, it.value(), "INPROGRESS", first)
                    flag = true
                } else if (flag && it.name != status) {
                    createProgressItem(this, it.value(), "IDLE", first)
                } else {
                    createProgressItem(this, it.value(), "DONE", first)
                }

                first = false
            }
        }
    }

    fun createProgressItem(parent: HTMLDivElement, title: String, type: String, first: Boolean = false) {
        var imgurl = ""
        var circleClass = ""
        var iconName = ""

        when (type) {
            "IDLE" -> {
                iconName = "clock-outline"
                imgurl = "idle"
                circleClass = "progress-idle"
            }
            "INPROGRESS" -> {
                iconName = "autorenew"
                imgurl = "inprogress"
                circleClass = "progress-inprogress"
            }
            "DONE" -> {
                iconName = "check"
                imgurl = "done"
                circleClass = "progress-done"
            }
        }

        if (!first) {
            parent.div {
                addClass("progress-arrow")
                img("img/progress/$imgurl.svg") {
                    addClass("progress-arrow-img")
                }
            }
        }
        parent.div {
            addClass("progress-item")
            div {
                addClass("progress-title")
                label(title)
            }
            div {
                addClass("progress-circle $circleClass")
                icon(iconName)
            }
        }
    }

    fun modifyState(
        state: Enums.Statuses,
        progress: HTMLDivElement,
        title: HTMLSpanElement,
        orderID: Int
    ) {
        val newState = object {
            val status: String = state.name
            val orderID: Int = orderID
        }
        ApiService.updateState(newState) {}
        progress.innerHTML = ""
        createProgress(state.name, progress)
        title.innerText = state.value()

    }

    fun getOrders() {
        ApiService.getAllOrders {
            destination.innerHTML = ""
            val data = it
            data.forEach { order ->
                createAccordion(destination, order)
            }
            initialize()
        }
    }

    override fun onShow() {
        getOrders()
    }
}