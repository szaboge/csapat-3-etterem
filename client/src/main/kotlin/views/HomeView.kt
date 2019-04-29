package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.img
import globals.ui.Lang
import globals.ui.RouterService
import globals.ui.Routes
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.addClass


class HomeView: View() {
    var slideIndex = 0
    var timeOut = 0

    private fun showSlides() {
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

        timeOut = window.setTimeout({
           showSlides()
        },5000)
    }

    override val routeType = Routes.HOME

    override fun onDestroy() {
        window.clearTimeout(timeOut)
    }

    override fun onShow() {
        with(root)
        {
            div {
                div{
                    addClass("slideshow-container")
                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/1.jpeg")
                        button(Lang.getText("home-title")) {
                            addClass("btn")
                            addEventListener("click", {
                                RouterService.navigate(Routes.RESTAURANTS)
                            })
                        }
                    }
                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/2.jpeg")
                        button(Lang.getText("home-title")) {
                            addClass("btn")
                            addEventListener("click", {
                                RouterService.navigate(Routes.RESTAURANTS)
                            })
                        }
                    }
                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/3.jpeg")
                        button(Lang.getText("home-title")) {
                            addClass("btn")
                            addEventListener("click", {
                                RouterService.navigate(Routes.RESTAURANTS)
                            })
                        }
                    }
                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/4.jpeg")
                        button(Lang.getText("home-title")) {
                            addClass("btn")
                            addEventListener("click", {
                                RouterService.navigate(Routes.RESTAURANTS)
                            })
                        }
                    }
                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/5.jpeg")
                        button(Lang.getText("home-title")) {
                            addClass("btn")
                            addEventListener("click", {
                                RouterService.navigate(Routes.RESTAURANTS)
                            })
                        }
                    }
                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/6.jpeg")
                        button(Lang.getText("home-title")) {
                            addClass("btn")
                            addEventListener("click", {
                                RouterService.navigate(Routes.RESTAURANTS)
                            })
                        }
                    }
                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/7.jpeg")
                        button(Lang.getText("home-title")) {
                            addClass("btn")
                            addEventListener("click", {
                                RouterService.navigate(Routes.RESTAURANTS)
                            })
                        }
                    }
                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/8.jpeg")
                        button(Lang.getText("home-title")) {
                            addClass("btn")
                            addEventListener("click", {
                                RouterService.navigate(Routes.RESTAURANTS)
                            })
                        }
                    }
                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/9.jpeg")
                        button(Lang.getText("home-title")) {
                            addClass("btn")
                            addEventListener("click", {
                                RouterService.navigate(Routes.RESTAURANTS)
                            })
                        }
                    }
                    div {
                        addClass("mySlides fade")
                        img("../webpage/img/homeview/10.jpeg")
                        button(Lang.getText("home-title")) {
                            addClass("btn")
                            addEventListener("click", {
                                RouterService.navigate(Routes.RESTAURANTS)
                            })
                        }
                    }
                }
            }
        }
        showSlides()
    }

    override fun render() {

    }
}
