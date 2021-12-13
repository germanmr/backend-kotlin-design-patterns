import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

abstract class Shape : Cloneable {
    var id: String? = null
    var type: String? = null

    abstract fun draw()
    public override fun clone(): Any {
        var clone: Any? = null
        try {
            clone = super.clone()
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return clone!!
    }
}

class Rectangle : Shape() {
    override fun draw() {
        println("Inside a Rectangle::draw method")
    }

    init {
        type = "Rectangle"
    }
}

class Square : Shape() {
    override fun draw() {
        println("Inside a Square::draw method")
    }

    init {
        type = "Square"
    }
}

class Circle : Shape() {
    override fun draw() {
        println("Inside a Circle::draw method")
    }

    init {
        type = "Circle"
    }
}

object ShapeCache {
    private val shapeMap = hashMapOf<String?, Shape>()

    fun loadCache() {
        val rectangle = Rectangle()
        val square = Square()
        val circle = Circle()

        shapeMap.put("1", rectangle)
        shapeMap.put("2", square)
        shapeMap.put("3", circle)
    }

    fun getShape(shapeId: String): Shape {
        val cachedShape = shapeMap.get(shapeId)
        return cachedShape?.clone() as Shape
    }
}

class PrototypeTest {
    @Test
    fun testPrototype() {
        ShapeCache.loadCache()
        val cloneShape1 = ShapeCache.getShape("1")
        val cloneShape2 = ShapeCache.getShape("2")
        val cloneShape3 = ShapeCache.getShape("3")

        cloneShape1.draw()
        cloneShape2.draw()
        cloneShape3.draw()

        Assertions.assertThat(cloneShape1.type).isEqualTo("Rectangle")
        Assertions.assertThat(cloneShape2.type).isEqualTo("Square")
        Assertions.assertThat(cloneShape3.type).isEqualTo("Circle")
    }
}