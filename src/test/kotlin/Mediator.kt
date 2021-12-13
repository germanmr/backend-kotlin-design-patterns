import org.junit.jupiter.api.Test

class ChatUser(
    private val mediator: Mediator,
    private val name: String
) {
    fun send(msg: String) {
        println("$name: Sending message: $msg")
        mediator.sendMessage(msg, this)
    }

    fun receive(msg: String) {
        println("$name: Receive message: $msg")
    }
}

class Mediator {
    private val users = arrayListOf<ChatUser>()

    fun sendMessage(msg: String, user: ChatUser) {
        users
            .filter { it != user }
            .forEach { it.receive(msg) }
    }

    fun addUser(user: ChatUser): Mediator =
        apply { users.add(user) }

}

class ChatMediatorTest {

    @Test
    fun testMediator() {
        val mediator = Mediator()

        val alice = ChatUser(mediator, "Alice")
        val bob = ChatUser(mediator, "Bob")
        val carol = ChatUser(mediator, "Carol")

        mediator.addUser(alice)
        mediator.addUser(bob)
        mediator.addUser(carol)

        carol.send("Hello everyone!")
    }
}