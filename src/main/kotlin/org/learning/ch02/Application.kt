package org.learning.ch02

import geometry.shapes.createRandomRectangle
import ch02.colors.Color
import ch02.colors.Color.*
import java.io.BufferedReader
import java.io.StringReader
import java.util.*

/*
* Statements and expressions
In Kotlin, `if` is an expression, not a statement. The difference between a
statement and an expression is that an expression has a value, which can
be used as part of another expression, whereas a statement is always a
top-level element in its enclosing block, and does not have its own value.
In Java, all control structures are statements. In Kotlin, most control
structures, except for the loops (for, do, and do/while) are expressions. The ability to combine control structures with other expressions lets you
express many common patterns concisely, as you’ll see later in the book. On the other hand, assignments are expressions in Java and become
statements in Kotlin. This helps avoid confusion between comparisons
and assignments, which is a common source of mistakes
*
* */

fun main(args: Array<String>) {
//    println(max(1,2))

//    val person = Person("Bob", true)
//    println(person.name)
//    println(person.isMarried)

//    val rectangle = Rectangle(41,43)
//    println(rectangle.isSquare)
//
//    println(createRandomRectangle().isSquare)

//    println(Color.BLUE.rgb())
//    println(getMnemonic(Color.RED))
//    println(getWarmth(Color.GREEN))

//    println(mix(RED, YELLOW))
//    println(mix(BLUE, YELLOW))
//    println(mix(RED, GREEN))

    //smart casts
//    println(eval(Sum(Sum(Num(1), Num(2)), Num(4))))
//    println(eval(Num(2)))

    //Block as branch
//    println(evalWithLogging(Sum(Sum(Num(1), Num(2)), Num(4))))

    // Ranges
//    for(i in 1..100) {
//        print(fizzBuzz(i))
//    }

//    for(i in 100 downTo 1 step 2) {
//        print(fizzBuzz(i))
//    }

    //Iterating over maps
//    val binaryReps = TreeMap<Char, String>()
//    for (c in 'A'..'F') {
//        val binary = Integer.toBinaryString(c.toInt())
//        binaryReps[c] = binary
//    }
//    for((letter, binary) in binaryReps) {
//        println("$letter = $binary")
//    }
//
//    val list = arrayListOf("10", "11", "1001")
//    for((index, element) in list.withIndex()) {
//        println("$index : $element")
//    }

//    println("Kotlin" in "Java".."Scala")

    // Try catch n finally
//    val reader = BufferedReader(StringReader("239"))
//    println(readNumber(reader))

    // try as an expression
//    val readerNotNumber = BufferedReader(StringReader("not a number"))
//    readNumber(readerNotNumber)
}

// Functions
//fun max(a: Int, b: Int): Int {
//    return if (a > b) a else b
//}

// Short version
fun max(a: Int, b: Int): Int = if ( a > b) a else b

// Properties
class Person(
        val name: String,
        val isMarried: Boolean
)

//Custom accessors
class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() {
            return height == width
        }
}

// enum class
//enum class Color {
//    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
//}

//enum class Color (val r: Int, val g: Int, val b: Int) {
//
//    RED(225,0,0), ORANGE(255,165,0),
//    YELLOW(255, 255, 0), GREEN(0, 255, 0), BLUE(0, 0, 255),
//    INDIGO(75, 0, 130), VIOLET(238, 130, 238);
//    fun rgb() = (r * 256 + g) * 256 + b
//}

fun getMnemonic(color: Color) =
        when (color) {
            Color.RED -> "Richard"
            Color.ORANGE -> "Of"
            Color.YELLOW -> "York"
            Color.GREEN -> "Gave"
            Color.BLUE -> "Battle"
            Color.INDIGO -> "In"
            Color.VIOLET -> "Vain"
        }
fun getWarmth(color: Color) = when(color) {
    RED, ORANGE, YELLOW -> "warm"
    GREEN -> "neutral"
    BLUE, INDIGO, VIOLET -> "cold"
}

fun mix(c1: Color, c2: Color) = when(setOf(c1,c2)) {
    setOf(RED, YELLOW) -> ORANGE
    setOf(YELLOW, BLUE) -> GREEN
    setOf(BLUE, VIOLET) -> INDIGO
    else -> throw Exception("Dirty color")
}

fun mixOptimized(c1: Color, c2: Color) = when {
    (c1 == RED && c2 == YELLOW) ||
            (c1 == YELLOW && c2 == RED) ->
        ORANGE
    (c1 == YELLOW && c2 == BLUE) ||
            (c1 == BLUE && c2 == YELLOW) ->
        GREEN
    (c1 == BLUE && c2 == VIOLET) ||
            (c1 == VIOLET && c2 == BLUE) ->
        INDIGO
    else -> throw Exception("Dirty color")
}

// smart casts: combining type checks and casts
interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

//fun eval(e: Expr): Int {
//    if (e is Num) {
//        val n = e as Num
//        return n.value
//    }
//    if (e is Sum) {
//        return eval(e.right) + eval(e.left)
//    }
//    throw IllegalArgumentException("Unknown expression")
//}

// Refactor
//fun eval(e: Expr): Int =
//        if(e is Num) {
//            e.value
//        } else if (e is Sum) {
//            eval(e.right) + eval(e.left)
//        } else {
//            throw IllegalArgumentException("Unknown expression")
//        }

// Polish
fun eval(e: Expr): Int = when (e) {
    is Num ->
        e.value
    is Sum ->
        eval(e.right) + eval(e.left)
    else ->
        throw IllegalArgumentException("Unknown expression")
}

fun evalWithLogging(e: Expr): Int =
        when (e) {
            is Num -> {
                println("num: ${e.value}")
                e.value
            }
            is Sum -> {
                val left = evalWithLogging(e.left)
                val right = evalWithLogging(e.right)
                println("sum: $left + $right")
                left + right
            }
            else -> throw IllegalArgumentException("Unknown expression")
        }

// Ranges
val oneToTen = 1..10
fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz "
    i % 3 == 0 -> "Fizz "
    i % 5 == 0 -> "Buzz "
    else -> "$i "
}

// Progression
//for (x in 0 until size) ~ for (x in 0..size-1)

// Using an 'in' check
fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigit(c: Char) = c !in '0'..'9'
fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "It's a digit!"
    in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
    else -> "I don't know"
}

// Exceptions
//if (percentage !in 0..100) {
//    throw IllegalArgumentException("A percentage value must be between 0 and 100: $percentage")
//}
fun checkNumber(number: Int) {
    val percentage =
        if (number in 0..100)
            number
        else
            throw IllegalArgumentException("A percentage value must be between 0 and 100: $number")
}
//
//fun readNumber(reader: BufferedReader): Int? {
//    try {
//        val line = reader.readLine()
//        return Integer.parseInt(line)
//    }
//    catch (e: NumberFormatException) {
//        return null
//    }
//    finally {
//        reader.close()
//    }
//}

// 'try' as an expression

fun readNumber(reader: BufferedReader) {
    val number = try {
        Integer.parseInt(reader.readLine())
    }
    catch (e: NumberFormatException) {
//        return or
        null
    }
    println(number)
}

/*
* Summary
*
*   - The fun keyword is used to declare a function. The val and var keywords declare
*   read-only and mutable variables, respectively.
*   - String templates help you avoid noisy string concatenation. Prefix a variable name with $
*   or surround anexpression with ${ } to have its value injected into string.
*   - Value-object classes are expressed in a very concise way in kotlin.
*   - The familiar if is now an expression with a return value.
*   - The when expression is analogous to switch in Java but is more powerful.
*   - You don't have to cast a variable expicity after checking that it has a certain type:
*   the compiler casts it for you automatically using a smart cast.
*   - The for, while and do-while loops are very similar to Java, but the for loop is now more
*   convenient, esp when you need to iterate over a map or a collection with an index.
*   - The concise syntax 1..5 creates a range. Ranges and progressions allow Kotlin to use a
*   uniform syntax and set of abstractions in for loops and also work with the in and !in
*   operators that check whether a value belongs to a range.
*   - Exception handling in Kotlin is very similar to Java, except that Kotlin doesn't require
*   you to decleare the exception that can be thrown by a method.
*   */
