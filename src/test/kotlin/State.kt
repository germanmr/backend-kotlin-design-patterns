import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

sealed class AuthorizationState

object UnauthorizedState : AuthorizationState()

class Authorized(val username: String) : AuthorizationState()

class AuthorizationPresenter {
    private var state: AuthorizationState = UnauthorizedState

    val isAuthorized: Boolean
        get() = when (state) {
            is Authorized -> true
            is UnauthorizedState -> false
        }

    val username: String
        get() {
            return when (val state = this.state) {
                is Authorized -> state.username
                is UnauthorizedState -> "Unknown"
            }
        }

    fun loginUser(username: String) {
        state = Authorized(username)
    }

    fun logoutUser() {
        state = UnauthorizedState
    }

    override fun toString(): String = "User is logged in: $isAuthorized"
}


class StateTest {
    @Test
    fun testStates() {
        val authorizationPresenter = AuthorizationPresenter()

        authorizationPresenter.loginUser("admin")
        println(authorizationPresenter)
        Assertions.assertThat(authorizationPresenter.isAuthorized).isTrue
        Assertions.assertThat(authorizationPresenter.username).isEqualTo("admin")

        authorizationPresenter.logoutUser()
        println(authorizationPresenter)
        Assertions.assertThat(authorizationPresenter.isAuthorized).isFalse
        Assertions.assertThat(authorizationPresenter.username).isEqualTo("Unknown")

    }
}