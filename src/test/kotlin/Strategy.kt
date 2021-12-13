import org.junit.jupiter.api.Test

class Printer(
    private val stringFormatterStrategy: (String) -> String
) {
    fun printString(string: String) {
        println(stringFormatterStrategy(string))
    }
}

val lowercaseFormatter = { it: String -> it.lowercase() }
val uppercaseFormatter = { it: String -> it.uppercase() }

class StrategyTest {
    @Test
    fun testStrategies() {
        val inputString = "LOREM ipsum DOLOR sit amet"

        val lowercasePrinter = Printer(lowercaseFormatter)

        lowercasePrinter.printString(inputString)

        val uppercasePrinter = Printer(uppercaseFormatter)
        uppercasePrinter.printString(inputString)

    }
}