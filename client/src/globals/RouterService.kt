package globals

import abstracts.View
import views.MainView
import views.RestaurantsView

enum class Routes {
    MAIN,
    RESTAURANTS,
    MENU
}

object RouterService {
    private val routes: MutableList<View> = mutableListOf()

    init {
        routes.add(MainView())
        routes.add(RestaurantsView())
    }

    fun navigate(route: Routes) {
        routes.find { it.routeType == route }?.build()?.show()
    }
}