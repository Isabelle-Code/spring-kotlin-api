package br.com.minions.person

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.StepVerifier

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonResourceTest(
        @Autowired private val webTestClient: WebTestClient
) {

    @Test
    fun `create account and retrieve it`() {
        val request = CreatePerson("name", "01920391232", 10)

        val body = webTestClient
                .post().uri("/person")
                .bodyValue(request)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(PersonInfo::class.java)
                .returnResult()
                .responseBody

        val getResult = webTestClient
                .get().uri("/person/" + body!!.id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(Person::class.java)
                .returnResult()

        val resultBody = getResult.responseBody

        assertAll(
                "Response from [GET] should be the same values as [POST] object.",
                { assertEquals(request.name, resultBody!!.name, "Name should be ${request.name}") },
                { assertEquals(request.document, resultBody!!.document, "Document should be ${request.document}") },
                { assertEquals(request.age, resultBody!!.age, "Age should be ${request.age}") },
        )

    }
}

