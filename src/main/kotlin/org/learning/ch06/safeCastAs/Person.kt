package org.learning.ch06.safeCastAs

// Safe casts: as?

fun main(args: Array<String>) {
    val p1 = Person("Dmitry", "Jemerov")
    val p2 = Person("Dmitry", "Jemerov")
    println(p1 == p2)
    println(p1.equals(42))
}
class Person(val firstName: String, val lastName: String) {
    override fun equals(o: Any?): Boolean {
        val otherPerson = o as? Person ?: return false // Checks the type and returns false if no match
        return otherPerson.firstName == firstName &&  //After the safe cast, the variable otherPerson is smart-cast to the Person type.
                otherPerson.lastName == lastName
    }
    override fun hashCode(): Int =
            firstName.hashCode() * 37 + lastName.hashCode()
}
