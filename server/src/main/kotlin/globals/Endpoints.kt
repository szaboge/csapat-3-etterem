package globals

import auth.Auth
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import database.DatabaseManager
import io.javalin.BadRequestResponse
import io.javalin.Context
import models.communication.FoodsCountModel
import models.communication.MakeOrderModel
import models.communication.UserByTokenModel
import java.io.StringReader

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
        try {
            val json = Klaxon().parseJsonObject(StringReader(body))
            val array = json["foods"] as JsonArray<*>
            array.forEach { food ->
                if (food is JsonObject) {
                    myFoodsList.add(FoodsCountModel(food["foodID"].toString().toInt(), food["restaurantID"].toString().toInt(),
                        food["name"].toString(), food["count"].toString().toInt()))
                }
            }
            myOrderModel = MakeOrderModel(json["name"].toString(), json["phone"].toString(), myFoodsList)
            DatabaseManager.insertOrder(myOrderModel)
            ctx.status(200)
        }catch (e: RuntimeException) {
            ctx.status(400)
            e.printStackTrace()
        }
    }
}