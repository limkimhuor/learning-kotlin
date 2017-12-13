package org.learning.ch03

/*Defining and calling functions
* - Functions for working with collections, strings, and regular expressions
* - Using named arguments, default argument values, and the infix call syntax
* - Adapting Java libraries to Kotlin through extension functions and properties
* - Structuring your code with top-level and local functions and properties
*/

fun main(args: Array<String>) {
    val set = setOf(1, 7, 53)
    val list = listOf(1, 7, 53)
    val map = mapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

//    println(set.javaClass)
//    println(list.javaClass)
//    println(map.javaClass)
    val strings = listOf("first", "second", "fourteenth")
    val numbers = setOf(1, 14, 2)
//    println(strings.last())
//    println(numbers.max())
    // invoke to String()
//    println(list)

//    println(joinToString(list, ";", "(", ")"))
//
//    println(joinToString(list, ", ", "", ""))
//    println(joinToString(list))
//    println(joinToString(list, "; "))
//    println(joinToString(list, prefix = "# "))

    // Adding methods to other people’s classes: extension functions and properties
//    println("Kotlin".lastChar())

    // Unity functions as extensions
//    val listExtension = arrayListOf(1, 2, 3)
//    println(listExtension.joinToString(" "))

    // No overriding for extension functions
//    val view: View = Button()
//    view.click()

    // No overriding for extension functions
//    view.showOff()

    //Varargs: functions that accept an arbitrary number of arguments
//    var listVarargs = listOf(2, 3, 5, 7, 11)
//    var listVarargs = listOf("args: ", *args)
//    println(listVarargs)

    // Split strings
//    println("12.345-6.A".split("\\.|-".toRegex()))
//    println("12.345-6.A".split(".", "-"))

    parsePath("/Users/yole/kotlin-book/chapter.adoc")
//    val price = """${'$'}99.9"""
//    println(price)
}


//fun <T> joinToString(
//        collection: Collection<T>,
//        separator: String,
//        prefix: String,
//        postfix: String
//): String {
//    val result = StringBuilder(prefix)
//    for ((index, element) in collection.withIndex()) {
//        if (index > 0) result.append(separator)
//        result.append(element)
//    }
//    result.append(postfix)
//    return result.toString()
//}

// Add default value
//fun <T> joinToString(
//        collection: Collection<T>,
//        separator: String = ", ",
//        prefix: String = "",
//        postfix: String = ""
//): String {
//    val result = StringBuilder(prefix)
//    for ((index, element) in collection.withIndex()) {
//        if (index > 0) result.append(separator)
//        result.append(element)
//    }
//    result.append(postfix)
//    return result.toString()
//}

//fun String.lastChar(): Char = this.get(this.length - 1)
// `this` can omit it - "this" references are implicit
fun String.lastChar(): Char = get(length - 1)

// Imports and extension functions

/*
import strings.lastChar or import strings.*
val c = "Kotlin".lastChar()
// change name of the class or function
import strings.lastChar as last
val c = "Kotlin".last()
*/

// Unity functions as extensions
fun <T> Collection<T>.joinToString( //Declares an extension function on Collection<T>
        separator: String = ", ",
        prefix: String = "",
        postfix: String = ""
): String {
    val result = StringBuilder(prefix)

    for ((index, element) in this.withIndex()) { // "this" refers to the receiver object: a collection of T.
        if (index > 0)
            result.append(separator)
            result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun
        Collection<String>.join(
        separator: String = ", ",
        prefix: String = "",
        postfix: String = ""
) = joinToString(separator, prefix, postfix)
/*
>>> println(listOf("one", "two", "eight").join(" "))
one two eight
>!> listOf(1, 2, 8).join()
Error: Type mismatch: inferred type is List<Int> but Collection<String>
was expected.*/

// No overriding for extension functions
open class View {
    open fun click() = println("View clicked")
}

class Button: View() {
    override fun click() = println("Button clicked")
}

fun View.showOff() = println("I'm a view!")
fun Button.showOff() = println("I'm a button!")

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }
/*
* >>> println("Kotlin".lastChar)
n
>>> val sb = StringBuilder("Kotlin?")
>>> sb.lastChar = '!'
>>> println(sb)
Kotlin!
*/

// Extending the java collections API
/*
* >>> val strings: List<String> = listOf("first", "second", "fourteenth")
>>> strings.last()
fourteenth
>>> val numbers: Collection<Int> = setOf(1, 14, 2)
>>> numbers.max()
14
*/
//fun <T> List<T>.last(): T { /* returns the last element */ }
//fun Collection<Int>.max(): Int { /* finding a maximum in a collection */ }


infix fun Any.to(other: Any) = Pair(this, other)
//deructuring declaration
//val (number, name) = 1 to "one"

// Split string

fun parsePath(path: String) {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")

    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")
    println("Dir: $directory, name: $fileName, ext: $extension")
}

fun parsePathRegexp(path: String) {
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult != null) {
        val (directory, filename, extension) = matchResult.destructured
        println("Dir: $directory, name: $filename, ext: $extension")
    }
}

//Multiline triple-quoted strings
/*
*
val kotlinLogo = """| //
.|//
.|/ \"""
>>> println(kotlinLogo.trimMargin("."))
| //
|//
|/ \
*/

/*
class User(val id: Int, val name: String, val address: String)
fun saveUser(user: User) {
    fun validate(value: String, fieldName: String)
    { if (value.isEmpty()) {
        throw IllegalArgumentException(
                "Can't save user ${user.id}: " +
                        "$fieldName is empty")
    }
    }
    validate(user.name, "Name")
    validate(user.address, "Address")
// Save user to the database
}
>>> saveUser(User(1, "", ""))
java.lang.IllegalArgumentException: Cannot save user 1: Name is empty
*/

class User(val id: Int, val name: String, val address: String)
fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String)
    { if (value.isEmpty()) {
        throw IllegalArgumentException(
                "Can't save user $id: empty $fieldName")
    }
    }
    validate(name, "Name")
    validate(address, "Address")
}
fun saveUser(user: User)
{ user.validateBeforeSave()
// Save user to the database
}

// Summary
/*
* - Kotlin doesn’t define its own collection classes and instead extends the Java collection classes with a richer API.
* - Defining default values for function parameters greatly reduces the need to define overloaded functions, and the
* named-argument syntax makes calls to functions with many parameters much more readable.
* - Extension functions and properties let you extend the API of any class, including classes
* defined in external libraries. without modifying its source code and with no runtime overhead.
* - Infix call provide a clean syntax for calling operator-like methods with a single argument.
* - Kotlin calls provide a large number of convenient string-handling functions for both regular
* expressions and plain strings.
* - Triple-quoted strings provide a clean way to write expressions that would require a lot of noisy
* escaping and string concatenation in Java.
* - Local functions help you structure your code more cleanly and eliminate duplication.
*/
