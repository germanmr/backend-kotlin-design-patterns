import org.junit.jupiter.api.Test

interface CoffeeMachine {
    fun makeSmallCoffee()
    fun makeLargeCoffee()
}

class NormalCoffeeMachine : CoffeeMachine {
    override fun makeSmallCoffee() {
        println("Normal coffee machine making small coffee")
    }

    override fun makeLargeCoffee() {
        println("Normal coffee machine making large coffee")
    }
}


//Decorator

class EnhanceCoffeeMachine(private val coffeeMachine: CoffeeMachine) : CoffeeMachine by coffeeMachine {
    //    Overriding behaviour
    override fun makeLargeCoffee() {
        println("Enhanced coffee machine making large coffee")
    }

    fun makeMilkCoffee() {
        println("Enhanced coffee machine making milk coffee")
        coffeeMachine.makeSmallCoffee()
        println("Enhanced coffee machine: Adding milk")
    }
}

class DecoratorTest {

    @Test
    fun testDecorator() {
        val normalMachine = NormalCoffeeMachine()
        val enhancedCoffeeMachine = EnhanceCoffeeMachine(normalMachine)

        enhancedCoffeeMachine.makeSmallCoffee()
        println("---------------------")
        enhancedCoffeeMachine.makeLargeCoffee()
        println("---------------------")
        enhancedCoffeeMachine.makeMilkCoffee()
    }
}