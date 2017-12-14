package org.learning.ch04.implementPropertiesDeclareinInterface.accessBacking_field

// Accessing a backing field from a getter or setter

class User(val name: String) {
    var address: String = "unspecified"
        set(value: String) {
            println("""
                Address was changed fro $name:
                "$field" -> "$value".""".trimIndent())
                field = value
        }
}

/*
* >>> val user = User("Alice")
>>> user.address = "Elsenheimerstraße 47, 80687 München"
Address was changed for Alice:
"unspecified" -> "Elsenheimerstraße 47, 80687 München".
*/

// Changing accessor visibility

class LengthCounter {
    var counter: Int = 0
    private set
    fun addWord(word: String) {
        counter += word.length
    }
}

/*
>>> val lengthCounter = LengthCounter()
>>> lengthCounter.addWord("Hi!")
>>> print(lengthCounter.counter)
3
*/
