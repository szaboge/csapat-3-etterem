package views

import controllers.RestaurantsController
import globals.Constants
import javafx.scene.paint.Color
import models.RestaurantModel
import tornadofx.*

class RestaurantsView: View() {
    private val controller: RestaurantsController by inject()
    override val root = vbox{
        setPrefSize(Constants.ScreenWidth, Constants.ScreenHeight)

        style {
            backgroundColor += Color.ALICEBLUE
        }

        flowpane {
            useMaxWidth = true
            style {
                backgroundColor += Color.CYAN
                paddingAll = 10.0
            }
            button("Éttermek"){
                action {
                    replaceWith<RestaurantsView>()
                }
            }
            button("Ételek"){
                action {
                    replaceWith<FoodsView>()
                }
            }
            button("Login"){
                action {
                    replaceWith<LoginView>()
                }
            }
        }
        vbox {
            useMaxWidth = true
            style {
                backgroundColor += Color.BISQUE
            }
            label("Hello RestaurantsView")
            button("refresh Restaurants") {
                action {
                    controller.getRestaurants()
                }
            }
            hbox {
                val restaurant = textfield()
                button("insert restaurant") {
                    action {
                        controller.insertRestaurant(restaurant.text)
                    }
                }
            }

            tableview(controller.restaurants){
                useMaxWidth = true
                column("RestaurantID",RestaurantModel::restaurantID)
                column("Name",RestaurantModel::name)
            }
        }
    }
}