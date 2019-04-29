package globals

class Enums {
    enum class Statuses(val s: String) {
        ARRIVING("Feldolgozás alatt"),
        MAKING("Készítés alatt"),
        SHIPPING("Szállítás alatt"),
        DONE("Kiszállítva")
    }
}