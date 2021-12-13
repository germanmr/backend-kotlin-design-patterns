import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

// TODO make abstract Chain that has a next handler?
interface HandlerChain {
    fun addHeader(inputHeader: String): String
}

class AuthenticationHeader(
    val token: String?,
    var next: HandlerChain? = null
) : HandlerChain {
    override fun addHeader(inputHeader: String): String =
        "$inputHeader ${System.lineSeparator()}Authorization: $token"
            .let { next?.addHeader(it) ?: it }
}

class ContentTypeHeader(val contentType: String, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String): String =
        "$inputHeader ${System.lineSeparator()}contentType: $contentType"
            .let { next?.addHeader(it) ?: it }
}

class BodyPayloaderHeader(val body: String, val next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String): String =
        "$inputHeader ${System.lineSeparator()}body: $body"
            .let { next?.addHeader(it) ?: it }
}


class ChainOfResponsibilityTest {
    @Test
    fun testChainOfResponsibility() {
        val authenticationHeader = AuthenticationHeader("123456")
        val contentTypeHeader = ContentTypeHeader("json")
        val bodyPayloaderHeader = BodyPayloaderHeader("{\"name\": \"German\"}")

        authenticationHeader.next = contentTypeHeader
        contentTypeHeader.next = bodyPayloaderHeader

        val messageWithAuthentication = authenticationHeader.addHeader("headers with Authentication")
        println(messageWithAuthentication)
        Assertions.assertThat(
            """
headers with Authentication 
Authorization: 123456 
contentType: json 
body: {"name": "German"}
    """.trimIndent()
        ).isEqualTo(messageWithAuthentication.trimIndent())

        println("----------------------------------------")

        val messageWithoutAuthentication = contentTypeHeader.addHeader("headers without Authentication")
        println(messageWithoutAuthentication)

        Assertions.assertThat(
            """
headers without Authentication 
contentType: json 
body: {"name": "German"}
    """.trimIndent()
        ).isEqualTo(messageWithoutAuthentication.trimIndent())
    }
}