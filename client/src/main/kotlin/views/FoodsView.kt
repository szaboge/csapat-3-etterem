package views

import ApiService
import abstracts.View
import globals.order.Basket
import globals.order.OrderService
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.icon
import globals.ui.ElementFactory.label
import globals.ui.ElementFactory.span
import globals.ui.Lang
import globals.ui.RouterService
import globals.ui.Routes
import models.database.FoodModel
import org.w3c.dom.HTMLDivElement
import kotlin.dom.addClass

class FoodsView: View() {
    override val routeType: Routes = Routes.FOODS
    private var foods: Array<FoodModel> = arrayOf()
    private var foodDest: HTMLDivElement = div()
    private var basketDest: HTMLDivElement = div()
    lateinit var priceDest: HTMLDivElement

    override fun onShow() {
        Basket.clear()
        ApiService.getFoods(OrderService.actualRestaurant) {
            foods = it
            generateFoods()
        }
    }

    override fun render() {
        root.addClass("foods-root")
        foodDest = root.div {
            addClass("food")
            div {
                addClass("title-container")
                label(Lang.getText("foods-foods")) {
                    addClass("title")
                }
            }
        }
        root.div{
            div {
                addClass("title-container")
                label(Lang.getText("foods-basket")) {
                    addClass("title")
                }
            }
            addClass("basket")
            basketDest = div {
                addClass("basket-item-container")
            }
            priceDest = div {
                addClass("price-dest")
            }
            div {
                addClass("order-button-wrapper")
                button(Lang.getText("foods-checkout")) {
                    addClass("default-button")
                    addEventListener("click", {
                        RouterService.navigate(Routes.CHECKOUT)
                    })
                }
            }
        }
    }

    private fun generateFoods() {
        with(foodDest) {
            div {
                addClass("food-item-container")
                foods.forEach {
                    div {
                        addClass("food-item")
                        label(it.name)
                        div {
                            addClass("add-to-cart-container")
                            span("${it.price} Ft ")
                            icon("cart") {
                                addClass("icon-button to-basket")
                                addEventListener("click", { _ -> addToBasket(it)})
                            }
                        }
                    }
                }
            }
        }

    }
    private fun generateBasket() {
        basketDest.innerHTML = ""
        priceDest.innerHTML = ""

        var sum = 0
        Basket.getFoods().forEach {
            basketDest.div {
                addClass("basket-item")
                label(it.second.name)
                div {
                    addClass("basket-item-price")
                    icon("minus-circle-outline") {
                        addClass("icon-button to-basket remove-basket")
                        addEventListener("click", { _ ->
                            run {
                                Basket.removeFood(it)
                                generateBasket()
                            }
                        })
                    }
                    label("${it.third} x ")
                    label("${it.second.price} Ft = ")
                    label("${it.second.price * it.third} Ft")
                }
            }
            sum += it.second.price * it.third
        }
        priceDest.span("${Lang.getText("foods-sum")}: $sum Ft"){
            addClass("price-sum")
        }
    }

    private fun addToBasket(food: FoodModel) {
        Basket.addFood(food)
        generateBasket()
    }
}