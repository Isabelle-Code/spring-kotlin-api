package br.com.minions.person

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : ReactiveCrudRepository<Person, Int> {
}