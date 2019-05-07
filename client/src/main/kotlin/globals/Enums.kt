package globals

class Enums {
    enum class Statuses {
        ARRIVING{
            override fun value(): String = "Feldolgozás alatt"
        },
        MAKING {
            override fun value(): String = "Készítés alatt"
        },
        SHIPPING{
            override fun value(): String = "Szállítás alatt"
        },
        DONE {
            override fun value(): String = "Kiszállítva"
        };
        abstract fun value(): String
    }
    enum class Roles(public val s: String) { USER ("Felhasználó"), ADMIN("Admin"), KITCHEN("Konyha"), GUEST("Vendég"), RIDER("Futár") }
}