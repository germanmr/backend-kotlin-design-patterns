import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ComplexSystemStore(private val filePath: String) {
    private val cache: HashMap<String, String>

    init {
        println("Reading data from the file: $filePath")
        cache = HashMap()
        //Read properties from file and put into cache
    }

    fun store(key: String, value: String) {
        cache[key] = value
    }

    fun read(key: String) = cache[key] ?: ""

    fun commit() = println("Storing cached data to file $filePath")
}

data class User(val login: String)

//Facade -- En easier way to use the underlying layer
class UserRepository {
    private val systemPreferences = ComplexSystemStore("/data/user.prefers")

    fun save(user: User) {
        systemPreferences.store("USER_KEY", user.login)
        systemPreferences.commit()
    }

    fun findFirst(): User = User(systemPreferences.read("USER_KEY"))
}

class FacadeTest {

    @Test
    fun testFacade() {
        val userRepository = UserRepository()
        val user = User("john")
        userRepository.save(user)

        val retrieveUser = userRepository.findFirst()

        Assertions.assertThat(retrieveUser.login).isEqualTo("john")
    }
}