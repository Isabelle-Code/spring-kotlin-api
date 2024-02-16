package br.com.minions.person

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PersonService(
        private val repository: PersonRepository
) {
    fun create(acc: Person): Mono<Person> {
        return repository.save(acc)
    }

    fun findOne(id: Int): Mono<Person> {
        return repository.findById(id)
    }
}