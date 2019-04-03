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
    private var foods: Array<FoodModel> = arrayOf()
    private var foodDest: HTMLDivElement = div()
    private var basketDest: HTMLDivElement = div()

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
                button("Proceed to Checkout") {
                    addClass("default-button")
                    addEventListener("click", {
                        OrderService.makeOrder()
                    })
                }
            }
        }
    }

    private fun generateFoods() {
        foods.forEach {
            foodDest.div {
                addClass("food-item")
                label(it.name)
                button("Add to Basket") {
                    addClass("default-button")
                    addEventListener("click", { _ -> addToBasket(it)})
                }
            }
        }
    }
    private fun generateBasket() {
        while (basketDest.firstChild != null) basketDest.removeChild(basketDest.firstChild!!)
        Basket.getFoods().forEach {
            basketDest.div {
                addClass("basket-item")
                label(it.second.name)
                label(it.third.toString())
            }
        }
    }

    private fun addToBasket(food: FoodModel) {
        Basket.addFood(food)
        generateBasket()
    }
}