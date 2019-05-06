package globals

import auth.Auth
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import database.DatabaseManager
import io.javalin.BadRequestResponse
import io.javalin.Context
import io.javalin.ForbiddenResponse
import io.javalin.UnauthorizedResponse
import models.communication.FoodsCountModel
import models.communication.MakeOrderModel
import models.communication.UserByTokenModel
import java.io.StringReader
import java.util.regex.Pattern

object Endpoints {

    fun getRestaurants(ctx: Context) {
        ctx.json(DatabaseManager.getRestaurants())
    }

    fun getFoods(ctx: Context) {
        val restID = ctx.pathParam("id")
        ctx.json(DatabaseManager.getFoods(restID.toInt()))
    }

    fun getOrders(ctx: Context) {
        val user = getUser(ctx)!!
        ctx.json(DatabaseManager.getOrders(user.restaurantID))
    }

    fun getMyOrders(ctx: Context) {
        val user = getUser(ctx) ?: throw UnauthorizedResponse()
        val id = user.userID
        ctx.json(DatabaseManager.getOrdersByUserID(id))
    }

    fun getUser(ctx: Context): UserByTokenModel? {
        val token = Auth.getToken(ctx)
        return if (token != "") {
            val list = DatabaseManager.getUserByToken(token)
            if (list.count() > 0) {
                list.last()
            } else {
                null
            }
        } else {
            null
        }
    }

    fun authentication(ctx: Context) {
        val token = Auth.getToken(ctx)
        if (token != "") {
            val list = DatabaseManager.getUserByToken(token)
            if (list.count() > 0) {
                ctx.json(list.last())
            } else {
                throw UnauthorizedResponse()
            }
        } else {
            throw UnauthorizedResponse()
        }
    }

    fun login(ctx: Context) {
        val body = ctx.body()
        val json = try {
            Klaxon().parseJsonObject(StringReader(body))
        } catch (e: Exception) {
            throw BadRequestResponse()
        }

        val username: String = json["email"].toString()
        val password: String = json["password"].toString()

        val userList = DatabaseManager.getLoginUser(username,Utils.createPassword(password))

        if(userList.count() > 0) {
            val user = userList.last()
            val sessionID = Auth.login(user.userID, ctx)
            ctx.json(UserByTokenModel(user.userID,user.name!!, user.role, sessionID, user.restaurantID))
        } else {
            throw BadRequestResponse()
        }
    }

    fun insertRestaurant(ctx: Context) {
        val body = ctx.body()
        val json = try {
            Klaxon().parseJsonObject(StringReader(body))
        } catch (e: Exception) {
            throw BadRequestResponse()
        }
        val name: String = json["name"].toString()
        DatabaseManager.insertRestaurant(name)
    }

    fun insertOrder(ctx: Context){
        val userID: Int
        val body = ctx.body()
        val myOrderModel: MakeOrderModel?
        val myFoodsList: MutableList<FoodsCountModel> = mutableListOf()
        val pricesOfFoods: Map<Int, Int> = DatabaseManager.getPriceByFoodID()
        try {
            val json = Klaxon().parseJsonObject(StringReader(body))

            when(json.keys.contains("name") && json.keys.contains("email")
                    && json.keys.contains("phone") && json.keys.contains("zipcode") && json.keys.contains("city")
                    && json.keys.contains("street") && json.keys.contains("strnumber") && json.keys.contains("payment")) {
                false -> throw BadRequestResponse()
            }

            val validName = Utils.isNameValid(json["name"].toString())
            val validEmail = Utils.isEmailValid(json["email"].toString())
            val validPhone = Utils.isPhoneValid(json["phone"].toString())
            val validZipcode = Utils.isZipcodeValid(json["zipcode"].toString().toInt())
            val validCity = Utils.isCityValid(json["city"].toString())
            val validStreet = Utils.isStreetValid(json["street"].toString())
            val validStrnumber = Utils.isStrnumberValid(json["strnumber"].toString())
            val validPayment = Utils.isPaymentValid(json["payment"].toString())

            when (validName && validEmail && validPhone && validZipcode && validCity && validStreet
                && validStrnumber && validPayment) {
                false -> throw BadRequestResponse()
            }
            val user = getUser(ctx) ?: DatabaseManager.insertGuest(json["name"].toString(), json["email"].toString())
            userID = user.userID
            val array = json["foods"] as JsonArray<*>
            array.forEach { food ->
                if (food is JsonObject) {
                    println(food.toJsonString(true))
                    myFoodsList.add(FoodsCountModel(food["foodID"].toString().toInt(), food["restaurantID"].toString().toInt(),
                        food["name"].toString(), food["count"].toString().toInt(),
                        pricesOfFoods[food["foodID"].toString().toInt()] ?: throw BadRequestResponse()))
                }
            }
            myOrderModel = MakeOrderModel(json["name"].toString(),
                                        json["email"].toString(),
                                        json["phone"].toString(),
                                        json["zipcode"].toString().toInt(),
                                        json["city"].toString(),
                                        json["street"].toString(),
                                        json["strnumber"].toString(),
                                        json["payment"].toString(),
                                        userID,
                                        "ARRIVING",
                                        myFoodsList)
            DatabaseManager.insertOrder(myOrderModel)
        }catch (e: RuntimeException) {
            e.printStackTrace()
            throw BadRequestResponse()
        }
    }

    fun register(ctx: Context){
        val body = ctx.body()
        val json = try {
            Klaxon().parseJsonObject(StringReader(body))
        } catch (e: Exception) {
            throw BadRequestResponse()
        }
        val name: String = json["name"].toString()
        val email: String = json["email"].toString()
        val password: String = json["password"].toString()

        //check validation
        val validEmail: Boolean = Utils.isEmailValid(email)
        val validPsw: Boolean = Utils.isPasswordValid(password)

        when(name.isNotEmpty() && name.length < 41 && validEmail && email.length < 41 && validPsw) {
            true -> DatabaseManager.makeUser(name, email, Utils.createPassword(password))
            else -> throw BadRequestResponse()
        }
    }

    fun updateStatus(ctx: Context){
        val body = ctx.body()
        val json = try {
            Klaxon().parseJsonObject(StringReader(body))
        } catch (e: Exception) {
            throw BadRequestResponse()
        }
        val status: String = json["status"].toString()
        val orderID: Int = json["orderID"].toString().toInt()
        DatabaseManager.updateStatus(status, orderID)
    }

    fun deleteUser(ctx: Context){
        val body = ctx.body()
        val json = try {
            Klaxon().parseJsonObject(StringReader(body))
        } catch (e: Exception) {
            throw BadRequestResponse()
        }
        val userID: Int = json["userID"].toString().toInt()
        DatabaseManager.deleteUser(userID)
    }

    fun getUserInfo(ctx: Context){
        val user = getUser(ctx)!!
        ctx.json(DatabaseManager.getUserInfo(user.userID))
    }

    fun getUsers(ctx: Context){
        ctx.json(DatabaseManager.getUsers())
    }

    fun deleteOrder(ctx: Context){
        val body = ctx.body()
        val json = try {
            Klaxon().parseJsonObject(StringReader(body))
        } catch (e: Exception) {
            throw BadRequestResponse()
        }
        val orderID: Int = json["orderID"].toString().toInt()
        DatabaseManager.deleteOrder(orderID)
    }

    fun modifyUserRole(ctx: Context){
        val body = ctx.body()
        val json = try {
            Klaxon().parseJsonObject(StringReader(body))
        } catch (e: Exception) {
            throw BadRequestResponse()
        }
        val role: String = json["role"].toString()
        val userID: Int = json["userID"].toString().toInt()
        var restaurantID: Int = json["restaurantID"].toString().toInt()
        if ((role == "USER") || (role == "ADMIN") || (role == "GUEST")) restaurantID = 0
        if (((role == "KITCHEN") || (role == "RIDER")) && restaurantID == 0) throw BadRequestResponse()
        DatabaseManager.modifyUserRole(role, userID, restaurantID)
    }

    fun getAllOrders(ctx: Context){
        ctx.json(DatabaseManager.getAllOrders())
    }

    fun addFood(ctx: Context){
        val body = ctx.body()
        val json = try {
            Klaxon().parseJsonObject(StringReader(body))
        } catch (e: Exception) {
            throw BadRequestResponse()
        }
        val restaurantID: Int = json["restaurantID"].toString().toInt()
        val name: String = json["name"].toString()
        val price: Int = json["price"].toString().toInt()
        DatabaseManager.addFood(restaurantID, name, price)
    }

    fun deleteFood(ctx: Context){
        val body = ctx.body()
        val json = try {
            Klaxon().parseJsonObject(StringReader(body))
        } catch (e: Exception) {
            throw BadRequestResponse()
        }
        val foodID: Int = json["foodID"].toString().toInt()
        DatabaseManager.deleteFood(foodID)
    }

    fun deleteRestaurant(ctx: Context){
        val body = ctx.body()
        val json = try {
            Klaxon().parseJsonObject(StringReader(body))
        } catch (e: Exception) {
            throw BadRequestResponse()
        }
        val restaurantID: Int = json["restaurantID"].toString().toInt()
        DatabaseManager.deleteRestaurant(restaurantID)
    }
}