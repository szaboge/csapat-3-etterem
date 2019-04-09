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
        ctx.json(DatabaseManager.getOrders())
    }

    fun getOrdersById(ctx: Context) {
        val oID = ctx.pathParam("id")
        ctx.json(DatabaseManager.getFoodsByOrder(oID.toInt()))
    }

    fun getUser(ctx: Context) {

    }

    fun authentication(ctx: Context) {
        val token = Auth.getToken(ctx)
        //DatabaseManager.startGuestSession(Auth.genToken())
        if (token != "") {
            val list = DatabaseManager.getUserByToken(token)
            if (list.count() > 0) {
                ctx.json(list.last())
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
            ctx.json(UserByTokenModel(user.userID, user.role, sessionID))
        } else {
            throw BadRequestResponse()
        }
    }

    fun insertRestaurant(ctx: Context) {
        val name = ""
        DatabaseManager.insertRestaurant(name)
    }

    fun insertOrder(ctx: Context){
        val body = ctx.body()
        val myOrderModel: MakeOrderModel?
        val myFoodsList: MutableList<FoodsCountModel> = mutableListOf()
        val pricesOfFoods: List<Pair<Int, Int>> = DatabaseManager.getPriceByFoodID()
        try {
            val json = Klaxon().parseJsonObject(StringReader(body))
            val array = json["foods"] as JsonArray<*>
            array.forEach { food ->
                if (food is JsonObject) {
                    println(food.toJsonString(true))
                    myFoodsList.add(FoodsCountModel(food["foodID"].toString().toInt(), food["restaurantID"].toString().toInt(),
                        food["name"].toString(), food["count"].toString().toInt(), pricesOfFoods.indexOf(food["foodID"])))
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
}