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
    REGISTRATIONDONE,
    MYORDERS,
    RIDERORDERS,
    KITCHENORDERS
}

object RouterService {
    private val routes: MutableList<View> = mutableListOf()
    private var previous: View? = null

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
        routes.add(MyOrdersView())
        routes.add(RiderOrdersView())
        routes.add(KitchenOrdersView())
    }

    fun navigate(route: Routes) {
        previous?.onDestroy()
        previous = routes.find { it.routeType == route }?.show()
    }
}