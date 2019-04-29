package views

import ApiService
import abstracts.View
import globals.Enums
import globals.ui.ElementFactory
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.icon
import globals.ui.ElementFactory.img
import globals.ui.ElementFactory.label
import globals.ui.ElementFactory.span
import globals.ui.Routes
import models.communication.GetOrderModel
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.dom.addClass

class MyOrdersView : View() {
    override val routeType: Routes = Routes.MYORDERS
    lateinit var destination: HTMLDivElement

    override fun render() {
        with(root) {
            addClass("myorders-root")
            div {
                addClass("myorders-container")
                label("My Orders") {
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
        with(parent) {
            button {
                addClass("myorders-accordion")
                div {
                    addClass("myorders-accordion-title")
                    div {
                        span(element.orderID.toString() + " ")
                        span(element.name)
                        span(element.date)
                    }
                    span(element.status)
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
                    div {
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
                                icon("truck")
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
                                icon("truck")
                            }
                            div {
                                addClass("myorders-info-address")
                                span(element.name)
                                span(element.email)
                                span(element.payment)
                                span(element.phone)
                            }
                        }
                        div {
                            addClass("myorders-info")
                            div {
                                addClass("myorders-info-title")
                                label("Ételek")
                                icon("truck")
                            }
                            div {
                                addClass("myorders-info-address")
                                element.foods.forEach { food ->
                                    div {
                                        span(food.name)
                                        span(food.count.toString())
                                        span(food.price.toString())
                                    }
                                }
                            }
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
                    createProgressItem(this, it.s, "DONE", first)
                } else if (it.name == status) {
                    createProgressItem(this, it.s, "INPROGRESS", first)
                    flag = true
                } else if (flag && it.name != status) {
                    createProgressItem(this, it.s, "IDLE", first)
                } else {
                    createProgressItem(this, it.s, "DONE", first)
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

    override fun onShow() {
        ApiService.getMyOrders {
            val data = it
            data.forEach { order ->
                createAccordion(destination, order)
            }
            initialize()
        }

    }
}