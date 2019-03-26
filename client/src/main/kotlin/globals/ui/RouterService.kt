package globals.ui

import abstracts.View
import views.FoodsView
import views.MainView
import views.RestaurantsView

enum class Routes {
    MAIN,
    RESTAURANTS,
    MENU,
    FOODS,
    BASKET
}

object RouterService {
    private val routes: MutableList<View> = mutableListOf()

    init {
        routes.add(MainView())
        routes.add(RestaurantsView())
        routes.add(FoodsView())
    }

    fun navigate(route: Routes) {
        routes.find { it.routeType == route }?.show()
    }
}