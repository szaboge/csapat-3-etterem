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
    CHECKOUTWITHINFO,
    REGISTRATION,
    CHECKOUTDONE,
    REGISTRATIONDONE,
    MYORDERS,
    RIDERORDERS,
    KITCHENORDERS,
    ADMINORDERS,
    MODIFYUSERS
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
        routes.add(AdminOrdersView())
        routes.add(CheckoutWithInfoView())
        routes.add(ModifyUsersView())
    }

    fun navigate(route: Routes) {
        previous?.onDestroy()
        previous = routes.find { it.routeType == route }?.show()
    }
}