package globals.ui

import abstracts.View
import views.*

enum class Routes {
    HOME,
    RESTAURANTS,
    MENU,
    FOODS,
    ORDERS,
    LOGIN,
    USERCONTROL,
    CHECKOUT
}

object RouterService {
    private val routes: MutableList<View> = mutableListOf()

    init {
        routes.add(HomeView())
        routes.add(RestaurantsView())
        routes.add(FoodsView())
        routes.add(OrdersView())
        routes.add(LoginView())
        routes.add(CheckoutView())
    }

    fun navigate(route: Routes) {
        routes.find { it.routeType == route }?.show()
    }
}