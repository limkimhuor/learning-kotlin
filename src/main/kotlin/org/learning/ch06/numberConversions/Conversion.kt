package org.learning.ch06.numberConversions

import java.io.BufferedReader
import java.io.StringReader

// Number conversions
val i = 1
//val l: Long = i // Type mismatch.
val l: Long = i.toLong()
val x = 1

fun main(args: Array<String>) {
    println(x.toLong() in listOf(1L, 2L, 3L))

    fun foo(l: Long) = println(l)

    val b: Byte = 1
    val l = b + 1L
    foo(42)

    val reader = BufferedReader(StringReader("1\nabc\n42"))
    val numbers = readNumbers(reader)
    addValidNumbers(numbers)
}

// Any and Any?: the root types
val answer: Any = 42 //The value 42 is boxed, because Any is a reference type.
/*Note that Any is a non-nullable type, so a variable of the type Any can’t hold the value
null . If you need a variable that can hold any possible value in Kotlin, including null ,
you must use the Any? type.*/

// Nullability and collections
fun readNumbers(reader: BufferedReader): List<Int?> {
    val result = ArrayList<Int?>()
    for (lin in reader.lineSequence()) {
        try {
            val number = lin.toInt()
            result.add(number)
        }
        catch (e: NumberFormatException) {
            result.add(null)
        }
    }
    return result
}

/*The Kotlin way to write this is List<Int?>? , with two question
marks. You need to apply null checks both when using the value of the variable and
when using the value of every element in the list.*/

fun addValidNumbers(numbers: List<Int?>) {
    var sumOfValidNumbers = 0
    var invalidNumbers = 0
    for (number in numbers) {
        if (number != null) {
            sumOfValidNumbers +=
                    number
        } else {
            invalidNumbers++
        }
    }
    println("Sum of valid numbers: $sumOfValidNumbers")
    println("Invalid numbers: $invalidNumbers")
}
