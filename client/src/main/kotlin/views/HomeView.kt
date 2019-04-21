package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.img
import globals.ui.ElementFactory.label
import globals.ui.Lang
import globals.ui.RouterService
import globals.ui.Routes
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.addClass
import kotlin.dom.removeClass
import kotlin.js.Promise




class HomeView: View() {
    var slideIndex = 0

    private fun showSlides() {
        println(slideIndex)
        val slides = document.getElementsByClassName("mySlides")

        for(i in 0 until slides.length)
        {
            (slides[i]!! as HTMLDivElement).style.display="none"
        }

        slideIndex++
        if (slideIndex > slides.length-1) {
            slideIndex = 0
        }
        (slides[slideIndex]!! as HTMLDivElement).style.display = "block"

        window.setTimeout({
           showSlides()
        },5000)
        println(slideIndex)
    }

    override val routeType = Routes.HOME

    override fun onShow() {
        with(root)
        {
            div {
                div{
                    addClass("slideshow-container")

                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/first.jpeg")
                    }
                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/second.jpeg")
                    }
                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/third.jpeg")
                    }
                }
            }
        }
        println("onshowrun")
        showSlides()
    }

    override fun render() {

        with(root) {
            addClass("home")
            button(Lang.getText("home-title")) {
                addClass("default-button get-started")
                addEventListener("click", {
                    RouterService.navigate(Routes.RESTAURANTS)
                })
            }

        }

    }
}
