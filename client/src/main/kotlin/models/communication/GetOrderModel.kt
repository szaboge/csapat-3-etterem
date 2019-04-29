package models.communication

class GetOrderModel(val orderID: Int,
                    val date: String,
                    var name: String,
                    var email: String,
                    var phone: String,
                    var zipcode: Int,
                    var city: String,
                    var street: String,
                    var strnumber: String,
                    var payment: String,
                    val amount: Int,
                    var userID: Int,
                    var status: String,
                    var restaurantID: Int,
                    var foods: Array<FoodsCountModel>)