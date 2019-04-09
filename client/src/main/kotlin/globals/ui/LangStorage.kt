package globals.ui

class Word(hu: String, en: String) {
    fun getWord(lang: String): String = when {
        storage.containsKey(lang) -> storage[lang]!!
        else -> storage["hu"]!!
    }
    val storage: Map<String, String> = mapOf(
        "hu" to hu,
        "en" to en
    )
}

object LangStorage {
    fun getCollection(key: String): Word {
        return storage[key] ?: throw Exception("No key found")
    }
    val storage: Map<String, Word> = mapOf(
        "registration"                  to  Word("Regisztráció", "Registration"),
        "login"                         to  Word("Bejelentkezés", "Login"),
        "restaurants"                   to  Word("Éttermek", "Restaurants"),
        "logout"                        to  Word("Kijelentkezés", "Logout"),
        "menu-orders"                   to  Word("Rendelések", "Orders"),
        "home"                          to  Word("Kezdőlap", "Home"),
        "name"                          to  Word("Név", "Name"),
        "email"                         to  Word("Email cím", "Email"),
        "password"                      to  Word("Jelszó", "Password"),
        "repassword"                    to  Word("Jelszó megerősítése", "Confirm password"),

        "foods-foods"                   to  Word("Ételek", "Foods"),
        "foods-basket"                  to  Word("Kosár", "Basket"),
        "foods-checkout"                to  Word("Tovább a fizetéshez", "Proceed to Checkout"),
        "foods-add-to"                  to  Word("Kosárba", "Add to Basket"),
        "foods-sum"                     to  Word("Összesen", "Amount"),

        "restaurants-select"            to  Word("kiválasztás", "select"),

        "home-title"                    to  Word("RENDELÉS KEZDÉSE", "START ORDER"),

        "registration-hint"             to  Word("Legalább 8 karakter, kis- és nagybetű, szám", "At least 8 characters, lower- and uppercase, number"),
        "registration-error"            to  Word("Sikertelen regisztrálás, próbálja újra.", "Failed to register, try again."),
        "registration-done"             to  Word("SIKERES REGISZTRÁCIÓ", "REGISTRATION SUCCESSFUL"),

        "checkout-checkout-your-order"  to  Word("RENDELÉS BEFEJEZÉSE", "CHECKOUT YOUR ORDER"),
        "checkout-payment"              to  Word("FIZETÉSI MÓD", "PAYMENT"),
        "checkout-credit-card"          to  Word("BANKKÁRTYA", "CREDIT CARD"),
        "checkout-cash"                 to  Word("KÉSZPÉNZ", "CASH"),
        "checkout-order"                to  Word("RENDELÉS", "MAKE ORDER")
    )
}