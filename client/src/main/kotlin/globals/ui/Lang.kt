package globals.ui

object Lang {
    var actualLanguage = "hu"
    fun getText(key: String): String = LangStorage.getCollection(key).getWord(actualLanguage)
}