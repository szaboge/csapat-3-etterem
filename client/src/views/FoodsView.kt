package views

import ApiService
import abstracts.View
import globals.OrderService
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.Routes
import models.database.FoodModel

class FoodsView: View() {
    override val routeType: Routes = Routes.FOODS
    var foods: Array<FoodModel> = arrayOf()

    override fun onShow() {
        ApiService.getFoods(OrderService.actualRestaurant) {
            foods = it
            build()
        }
    }

    override fun render(): View {
        root.div {
            foods.forEach {
                div {
                    label(it.name)
                    button("Kosárba")
                }
            }
        }
        return this
    }
}