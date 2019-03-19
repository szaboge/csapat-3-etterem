package views


import globals.*
import javafx.scene.paint.Color
import tornadofx.*

class MainView: View() {
    val restaurantsView: RestaurantsView by inject()
    val loginView: LoginView by inject()
    val foodsView: FoodsView by inject()

    override val root = vbox {
        setPrefSize(Constants.ScreenWidth, Constants.ScreenHeight)
        flowpane {
            useMaxWidth = true
            style {
                backgroundColor += Color.CYAN
                paddingAll = 10.0
            }
            button("Éttermek") {
                action {
                    RouterService.navigate(RouteState.RESTAURANT)
                }
            }
        }
    }
    init {
        RouterService.root = root
        RouterService.routeItems = listOf(
            RouteItem("Étterem", restaurantsView, RouteState.RESTAURANT),
            RouteItem("Bejelentkezés", loginView, RouteState.LOGIN),
            RouteItem("Étlap", foodsView, RouteState.FOODS)
        )
        RouterService.navigate(RouteState.RESTAURANT)
    }
}