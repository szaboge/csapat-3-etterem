package models.communication

class MakeOrderModel(var name: String,
                     var email: String,
                     var phone: String,
                     var zipcode: Int,
                     var city: String,
                     var street: String,
                     var strnumber: String,
                     var payment: String,
                     var foods: List<FoodsCountModel>)