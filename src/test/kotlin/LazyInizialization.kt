import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


class AlertBox {
    var message: String? = null

    fun show() {
        println("Alert Bo $this: $message")
    }
}

class Window {
    val box by lazy { AlertBox() }

    fun showMessage(message: String) {
        box.message = message
        box.show()
    }
}

class WindowTest {
    @Test
    fun windowsTest() {
        val window = Window()
        window.showMessage("Hi")
        Assertions.assertThat(window.box).isNotNull
    }
}

class Window2 {
    lateinit var box: AlertBox

    fun showMessage(message: String) {
        box = AlertBox()
        box.message = message
        box.show()
    }
}

class Window2Test {
    @Test
    fun windowsTest() {
        val window2 = Window2()
//        println(window2.box)
        window2.showMessage("Hi")
        Assertions.assertThat(window2.box).isNotNull
    }
}



