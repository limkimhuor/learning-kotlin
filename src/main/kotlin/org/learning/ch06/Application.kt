package org.learning.ch06

import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JList

/* Ch6 The Kotlin type system
* Cover
* - Nullable types and syntax for dealing with nulls
* - Primitive types and their correspondence to the Java types
* - Kotlin collections and their relationship to Java
* */



fun main(args: Array<String>) {
    println(strLen("afas"))
    println(strLenSafe(null))
    printAllCaps("abc")
    printAllCaps(null)

    val ceo = Employee("Da Boss", null)
    val developer = Employee("Bob smith", ceo)

    println(managerName(developer))
    println(managerName(ceo))

    val person = Person("Dmitry", null)
    println(person.countryName())

    val address = Address("Elsestr. 47", 80687, "Munich", "Germany")
    val jetbrains = Company("JetBrains", address)
    val personElvis = Person("Dmitry", jetbrains)

    printShippingLabel(personElvis)
    //printShippingLabel(Person("Alexey", null))

    var email: String? = "yole@example.com"
    email?.let { sendEmailTo(it) }

    email = null
    email?.let { sendEmailTo(it) }

    showProgress(146)
}

//Nullability
fun strLen(s: String) = s.length

fun strLenSafe(s: String?): Int =
        if (s != null) s.length else 0

// Safe call operator: ?
fun printAllCaps(s: String?) {
    val allCaps: String? = s?.toUpperCase()
    println(allCaps)
}

class Employee(val name: String, val manager: Employee?)
fun managerName(employee: Employee): String? = employee.manager?.name

class Address(val streetAddress: String, val zipCode: Int,
              val city: String, val country: String)
class Company(val name: String, val address: Address?)
class Person(val name: String, val company: Company?)

fun Person.countryName(): String {
    val country = this.company?.address?.country // Several safe-access operators can be in a chain.
    return if (country != null) country else "unknown"
}

// Elvis operator: ?:
fun foo(s: String?) { val t: String = s ?: ""} //If "s" is null, the result is an empty string.

fun strLenSafeElvis(s: String?): Int = s?.length ?: 0
fun Person.countryNameElvis() =
        company?.address?.country ?: "Unknown"

fun printShippingLabel(person: Person) {
    val address = person.company?.address
        ?: throw IllegalArgumentException("No address")
    with(address) {
        println(streetAddress)
        println("$zipCode $city, $country")
    }
}


// Not-null assertions: !!
fun ignoreNulls(s: String?) {
    val sNotNull: String = s!!
    println(sNotNull.length)
}

class CopyRowAction(val list: JList<String>): AbstractAction() {
    override fun isEnabled(): Boolean =
        list.selectedValue != null

    override fun actionPerformed(e: ActionEvent) { //actionPerformed is called only if isEnabled returns "true".
        val value = list.selectedValue!!
        // copy value to clipboard
    }

}

/*
* person.company!!.address!!.country  // Don’t write code like this!
* */

// The let function

/*
>>> val email: String? = ...
>>> sendEmailTo(email)
ERROR: Type mismatch: inferred type is String? but String was expected

have to check explicitly whether this value isn’t null :
if (email != null) sendEmailTo(email)
*/

// Ao another way: use the let function, and call it via a safe call
/*
email?.let { email -> sendEmailTo(email) }
*/
fun sendEmailTo(email: String) {
    println("Sending email to $email")
}

val bestPerson: Person? = getTheBestPersonInTheWorld()
// getTheBestPersonInTheWorld()?.let { sendEmailTo(it.email) }
//if (bestPerson != null) sendEmailTo(bestPerson.email)
fun getTheBestPersonInTheWorld(): Person? = null

// late-initialized
class MyService {
    fun performAction(): String = "foo"
}

//class MyTest {
//    private var myService: MyService? = null //Declares a property of a nullable type to initialize it with null
//    @Before fun setup() {
//        myService = MyService() //Provides a real initializer in the setUp method
//    }
//
//    @Test fun testAction() {
//        Assert.assertEquals("foo", myService!!.performAction()) //You have to take care of nullability: use !! or ?.
//    }
//}

// Refactor with late-initialized
//class MyTest {
//    private lateinit var myService: MyService //Declares a property of a non-null type without an initializer
//    @Before fun setup() {
//        myService = MyService() //Initializes the property in the setUp method as before
//    }
//
//    @Test fun testAction() {
//        Assert.assertEquals("foo", myService.performAction()) //Accesses the property without extra null checks
//    }
//}

// Extensions on nullable types
fun verifyUserInput(input: String?) {
    if (input.isNullOrBlank()) { //No safe call is needed.
        // input ->  value of nullable type, isNullOrBlank -> extension on nullable type
        println("Please fill in the required fields")
    }
}

/*
>>> verifyUserInput(" ")
Please fill in the required fields
>>> verifyUserInput(null)  //No exception happens when you call isNullOrBlank with "null" as a receiver.
Please fill in the required fields
*/

// Nullability of type parameters
fun <T> printHashCode(t: T) {
    println(t?.hashCode()) // You have to use a safe call because "t" might be null.
}
/*
    >>> printHashCode(null) //"T" is inferred as "Any?".
    null
*/

fun <T: Any> printHashCodeSafe(t: T) {
    println(t.hashCode())
}
/*
>>> printHashCodeSafe(null)
Error: Type parameter bound for `T` is not satisfied
>>> printHashCodeSafe(42)
42
*/

// Nullability and Java
/*
* @Nullable + Type = Type?
* @NotNull + Type = Type
*       Java        Kotlin
* */
// PLATFORM TYPES
/*
* Type = Type? or Type
* Java      Kotlin
* */

// Inheritance
fun yellAt(person: Person) {
    println(person.name.toUpperCase() + "!!!")
}
/*
* lang.IllegalArgumentException: Parameter specified as non-null is null: method toUpperCase, parameter $receiver
*/

fun yellAtSafe(person: Person) {
    println((person.name ?: "Anyone").toUpperCase() + "!!!")
}
/*
* >>> yellAtSafe(Person(null))
* ANYONE!!!
* */
/* Java */


class StringPrinter : StringProcessor {
    override fun process(value: String) {
        println(value)
    }
}

// Primitive types: Int, Boolean and more
val i: Int = 1
val list: List<Int> = listOf(1, 2, 3)
fun showProgress(progress: Int) {
    val percent = progress.coerceIn(0, 100)
    println("We're ${percent}% done!")
}


