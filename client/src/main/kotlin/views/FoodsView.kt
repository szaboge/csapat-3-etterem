package views

import ApiService
import abstracts.View
import globals.order.Basket
import globals.order.OrderService
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.Routes
import models.database.FoodModel
import org.w3c.dom.HTMLDivElement
import kotlin.dom.addClass

class FoodsView: View() {
    override val routeType: Routes = Routes.FOODS
    var foods: Array<FoodModel> = arrayOf()
    var foodDest: HTMLDivElement = div()
    var basketDest: HTMLDivElement = div()

    override fun onShow() {
        ApiService.getFoods(OrderService.actualRestaurant) {
            foods = it
            generateFoods()
        }
    }

    override fun render(): View {
        root.addClass("foods-root")
        foodDest = root.div {
            addClass("food")
            div {
                addClass("foods-label-wrapper")
                label("FOODS") {
                    addClass("title")
                }
            }
        }
        root.div{
            label("BASKET") {
                addClass("title")
            }
            addClass("basket")
            basketDest = div {
                addClass("basket-item-container")
            }
            div {
                addClass("order-button-wrapper")
                button("ORDER") {
                    addClass("default-button")
                    addEventListener("click", {
                        OrderService.makeOrder()
                    })
                }
            }
        }
        return this
    }

    private fun generateFoods() {
        foods.forEach {
            foodDest.div {
                addClass("food-item")
                label(it.name)
                button("TO BASKET") {
                    addClass("default-button")
                    addEventListener("click", {event -> addToBasket(it)})
                }
            }
        }
    }
    private fun generateBasket() {
        while (basketDest.firstChild != null) basketDest.removeChild(basketDest.firstChild!!)
        Basket.basket.forEach {
            basketDest.div {
                addClass("basket-item")
                label(it.name)
            }
        }
    }

    private fun addToBasket(food: FoodModel) {
        Basket.addFood(food)
        generateBasket()
    }
}