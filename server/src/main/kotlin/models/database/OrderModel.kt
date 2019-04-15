package models.database

class OrderModel(val orderID: Int,
                 val date: String,
                 val name: String,
                 val email: String,
                 val phone: String,
                 val zipcode: Int,
                 val city: String,
                 val street: String,
                 val strnumber: String,
                 val payment: String,
                 val amount: Int,
                 val userID: Int,
                 val status: String)