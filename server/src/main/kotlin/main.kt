import io.javalin.Javalin

fun main() {
    val app = Javalin.create().start(7000)
    app.get("/") { it.json(object {
        val name = "Test Béla"
        val email = "test@test.com"
        val phone = "0620XXXXXXX"
    }) }
}