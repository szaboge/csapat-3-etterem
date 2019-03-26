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
        foodDest = root.div {
        }
        root.div{
            label("Kosár")
            basketDest = div {
            }
            button("Rendelés") {
                addEventListener("click", {
                    OrderService.makeOrder()
                })
            }
        }
        return this
    }

    private fun generateFoods() {
        foods.forEach {
            foodDest.div {
                label(it.name)
                button("Kosárba") {
                    addEventListener("click", {event -> addToBasket(it)})
                }
            }
        }
    }
    private fun generateBasket() {
        while (basketDest.firstChild != null) basketDest.removeChild(basketDest.firstChild!!)
        Basket.basket.forEach {
            basketDest.div {
                label(it.name)
            }
        }
    }

    private fun addToBasket(food: FoodModel) {
        Basket.addFood(food)
        generateBasket()
    }
}