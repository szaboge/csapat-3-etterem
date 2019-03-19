package globals

import javafx.scene.layout.VBox
import tornadofx.*
import views.FoodsView

class RouteItem(val name: String, val view: View, val state: RouteState)
enum class RouteState {
    FOODS,
    RESTAURANT,
    LOGIN
}

object RouterService {
    var routeItems: List<RouteItem> = listOf()
    var root: VBox = VBox()
    var actualItem: RouteItem? = null

    fun navigate(nextState: RouteState){
        actualItem?.view?.removeFromParent()
        val nextItem: RouteItem = routeItems.find { route -> route.state === nextState}!!
        if (nextItem.view is FoodsView) nextItem.view.refresh()
        root += nextItem.view
        actualItem = nextItem
    }
}