package globals.order

object OrderService {
    var actualRestaurant: Int = 0

    fun makeOrder() {
        ApiService.makeOrder(Basket.basket) {
            println(it)
        }
    }
}