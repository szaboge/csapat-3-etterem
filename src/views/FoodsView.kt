package views

import globals.Constants
import javafx.scene.paint.Color
import tornadofx.*

class FoodsView: View() {
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
        flowpane {
            useMaxWidth = true
            label("Hello FoodsView")
        }
    }
}