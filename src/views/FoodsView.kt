package views

import controllers.FoodsController
import globals.Basket
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.FlowPane
import javafx.scene.paint.Color
import tornadofx.*

class FoodsView : View() {
    fun refresh() {
        controller.getFoods()
        Basket.clear()
    }
    private val controller: FoodsController by inject()
    val basketView: BasketView by inject()
    var basket = FlowPane()
    override val root = vbox {
        useMaxWidth = true
        hbox {
            flowpane {
                vbox {
                    useMaxWidth = true
                    children.bind(controller.foods) {
                        hbox {
                            vboxConstraints { margin = Insets(6.0) }
                            style {
                                backgroundColor += Color.LIGHTBLUE
                                backgroundRadius += box(6.px)
                                paddingAll = 6.0
                            }
                            hbox {
                                alignment = Pos.CENTER_LEFT
                                label(it.name)
                            }
                            hbox {
                                alignment = Pos.CENTER_RIGHT
                                button("Kosárba") {
                                    action {
                                        Basket.addFood(it)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            basket = flowpane {

            }
        }
    }
    init {
        basket+= basketView
    }
}