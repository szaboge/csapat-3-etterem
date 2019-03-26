package globals.ui

import abstracts.View
import views.FoodsView
import views.HomeView
import views.OrdersView
import views.RestaurantsView

enum class Routes {
    HOME,
    RESTAURANTS,
    MENU,
    FOODS,
    ORDERS
}

object RouterService {
    private val routes: MutableList<View> = mutableListOf()

    init {
        routes.add(HomeView())
        routes.add(RestaurantsView())
        routes.add(FoodsView())
        routes.add(OrdersView())
    }

    fun navigate(route: Routes) {
        routes.find { it.routeType == route }?.show()
    }
}