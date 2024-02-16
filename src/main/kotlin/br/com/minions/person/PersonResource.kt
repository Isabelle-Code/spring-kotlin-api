package br.com.minions.person

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("person")
class PersonResource(
        private val service: PersonService
) {

    private val logger: Logger = LoggerFactory.getLogger(PersonResource::class.java)

    @GetMapping("/{id}")
    @ResponseBody
    fun create(@PathVariable("id") id: Int): Mono<Person> {
        return service
                .findOne(id)
                .doOnNext { logger.info("User found {}", it) }
    }

    @PostMapping
    @ResponseBody
    fun create(@RequestBody body: Mono<CreatePerson>): Mono<PersonInfo> {
        return body
                .doOnNext { logger.info("Request Body {}", it) }
                .map {
                    Person(
                            name = it.name,
                            document = it.document,
                            age = it.age
                    )
                }
                .flatMap { service.create(it) }
                .map { PersonInfo(it.id!!) }
    }
}
