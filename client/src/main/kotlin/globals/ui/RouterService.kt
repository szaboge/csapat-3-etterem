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
    CHECKOUT,
    REGISTRATION,
    CHECKOUTDONE,
    REGISTRATIONDONE
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
        routes.add(CheckoutDoneView())
        routes.add(RegistrationView())
        routes.add(RegistrationDoneView())
    }

    fun navigate(route: Routes) {
        routes.find { it.routeType == route }?.show()
    }
}