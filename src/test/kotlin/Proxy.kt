import org.junit.jupiter.api.Test

interface Image {
    fun display()
}

class RealImage(private val fileName: String) : Image {
    override fun display() {
        println("Real image: Displaying $fileName")
    }

    private fun loadFileFromDisk(fileName: String) {
        println("Real image: Loading")
    }

    init {
        loadFileFromDisk(fileName)
    }
}

class ProxyImage(private val filename: String) : Image {
    private var realImage: RealImage? = null

    override fun display() {
        println("Proxy Image: Displaying $filename")
        if (realImage == null) {
            realImage = RealImage(filename)
        }
        realImage!!.display()
    }
}

class ProxyTest {
    @Test
    fun testProxy() {
        val image = ProxyImage("test.jpg")

        image.display()
        println("---------------------")

        // load image from "cache"
        image.display()

    }
}