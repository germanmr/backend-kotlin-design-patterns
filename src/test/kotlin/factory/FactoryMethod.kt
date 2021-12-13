package factory

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

sealed class Country {
    object Canada : Country()
}

object Spain : Country()
class Greece(val someProperty: String) : Country()
data class USA(val someProperty: String) : Country()

class Currency(val name: String)

object CurrentFactory {
    fun currencyForCountry(country: Country): Currency =
        when (country) {
            is Spain -> Currency("EUR")
            is Greece -> Currency("EUR")
            is USA -> Currency("USD")
            is Country.Canada -> Currency("USD")
        }
}

class FactoryMethodTest {
    @Test
    fun currencyTest() {
        val greekCurrency = CurrentFactory.currencyForCountry(Greece("")).name
        println("Greek currency: $greekCurrency")

        val usaCurrency = CurrentFactory.currencyForCountry(USA("")).name
        println("usa currency: $usaCurrency")

        Assertions.assertThat(greekCurrency).isEqualTo("EUR")
        Assertions.assertThat(usaCurrency).isEqualTo("USD")

    }
}