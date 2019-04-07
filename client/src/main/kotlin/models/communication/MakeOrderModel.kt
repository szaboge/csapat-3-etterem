package models.communication

class MakeOrderModel(val name: String, var email: String, var phone: String, var zipcode: String, var city:String, var street: String, var strnumber:String, var payment:String,
                     var foods: List<FoodsCountModel>)

