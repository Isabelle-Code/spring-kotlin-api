package br.com.minions.person

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("PERSON")
data class Person(
        @Id
        var id: Int? = null,
        val name: String,
        val document: String,
        val age: Int
)

data class CreatePerson(
        val name: String,
        val document: String,
        val age: Int
)

data class PersonInfo(
        val id: Int
)