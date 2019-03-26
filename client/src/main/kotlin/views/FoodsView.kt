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
    var destination: HTMLDivElement = div()

    override fun onShow() {
        ApiService.getFoods(OrderService.actualRestaurant) {
            foods = it
            generateFoods()
        }
    }

    override fun render(): View {
        destination = root.div {
        }
        root.div{
            label("Basket")
        }
        return this
    }

    private fun generateFoods() {
        foods.forEach {
            destination.div {
                label(it.name)
                button("Kosárba") {
                    addEventListener("click", {event -> addToBasket(it)})
                }
            }
        }
    }

    fun addToBasket(food: FoodModel) {
        Basket.addFood(food)
    }
}