package views

import ApiService
import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.ElementFactory.span
import globals.ui.Routes
import models.communication.GetOrderModel
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.dom.addClass

class MyOrdersView: View() {
    override val routeType: Routes = Routes.MYORDERS
    lateinit var destination: HTMLDivElement

    override fun render() {
        with(root) {
            label("MyOrders")
            destination = div {

            }
        }
    }

    fun initialize() {
        val acc = document.getElementsByClassName("accordion")
        for (i in 0 until acc.length) {
            with(acc[i]!!) {
                val panel = nextElementSibling as HTMLDivElement
                panel.style.maxHeight = "0px"
                addEventListener("click", {
                    classList.toggle("active")
                    if (panel.style.maxHeight != "0px"){
                        panel.style.maxHeight = "0px"
                    } else {
                        panel.style.maxHeight = "${panel.scrollHeight}px"
                    }
                })
            }
        }
    }

    fun createAccordion(parent: HTMLElement, element: GetOrderModel) {
        with(parent) {
            button {
                addClass("accordion")
                span(element.orderID.toString())
                span(element.name)
                span(element.date)
            }
            div {
                addClass("panel")
                div {
                    span(element.zipcode.toString())
                    span(element.city)
                    span(element.street)
                    span(element.strnumber)
                }
                div {
                    span(element.email)
                    span(element.payment)
                    span(element.phone)
                }

                label("Kaják")
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