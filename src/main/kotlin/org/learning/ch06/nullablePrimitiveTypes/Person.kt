package org.learning.ch06.nullablePrimitiveTypes

// Nullable primitive: Int?, Boolean? and more
data class Person(val name: String, val age: Int? = null) {
    fun isOlderThan(other: Person): Boolean? {
        if (age == null || other.age == null)
            return null
        return age > other.age
    }
}

fun main(args: Array<String>) {
    println(Person("Sam", 35).isOlderThan(Person("Amy",42)))
    println(Person("Sam", 35).isOlderThan(Person("Jane")))
}