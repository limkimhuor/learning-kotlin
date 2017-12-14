package org.learning.ch04.universalObjectMethods

import com.sun.org.apache.xpath.internal.operations.Bool
import java.util.stream.Collector

//class Client(val name: String, val postalCode: Int){
//    //STRING REPRESENTATION: TOSTRING()
//    override fun toString() = "Client(name=$name, postalCode=$postalCode)"
///*
//>>> val client1 = Client("Alice", 342562)
//>>> println(client1)
//Client(name=Alice, postalCode=342562)
//*/
//
//    // OBJECT EQUALITY: EQUALS()
//    /*
//    >>> val client1 = Client("Alice", 342562)
//    >>> val client2 = Client("Alice", 342562)
//    >>> println(client1 == client2)
//    false
//    */
//}

//changed Client class
class Client(val name: String, val postalCode: Int){
    override fun equals(other: Any?): Boolean { // "Any" is the analogue of java.lang.Object: a superclass of all classes in Kotlin. The nullable type "Any?" means "other" can be null.
        if (other == null || other !is Client)
            return false
        return name == other.name && postalCode == other.postalCode
    }

    override fun toString() = "Client(name=$name, postalCode=$postalCode)"

    // fix hashCode()
    override fun hashCode(): Int = name.hashCode() * 31 + postalCode

    // HASH CONTAINERS: HASHCODE()
    /*
        >>> val processed = setOf(Client("Alice", 342562))
        >>> println(processed.contains(Client("Alice", 342562)))
        false
    */

    fun copy(name: String = this.name,
             postalCode: Int = this.postalCode) = Client(name, postalCode)

    /*
        >>> val bob = Client("Bob", 973293)
        >>> println(bob.copy(postalCode = 382555))
        Client(name=Bob, postalCode=382555)
    */
}

// Data classes
//data class Client(val name: String, val postalCode: Int)

// Class delegation: using the "by" keyword

//class DelegatingCollection<T> : Collection<T> {
//    private val innerList = arrayListOf<T>()
//
//    override val size: Int get() = innerList.size
//    override fun isEmpty(): Boolean = innerList.isEmpty()
//    override fun contains(element: T): Boolean = innerList.contains(element)
//    override fun iterator(): Iterator<T> = innerList.iterator()
//    override fun containsAll(elements: Collection<T>): Boolean = innerList.containsAll(elements)
//}

// Kotlin
//class DelegatingCollection<T>(innerList: Collection<T> = ArrayList<T>()) : Collection<T> by innerList

class CountingSet<T> (
        val innerSet: MutableCollection<T> = HashSet<T>()
) : MutableCollection<T> by innerSet { //Delegates the MutableCollection implementation to innerSet
    var objectsAdded = 0
    override fun add(element: T): Boolean { //Does not delegate; provides a different implementation
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(c: Collection<T>): Boolean {
        objectsAdded += c.size
        return innerSet.addAll(c)
    }
}

/*
>>> val cset = CountingSet<Int>()
>>> cset.addAll(listOf(1, 1, 2))
>>> println("${cset.objectsAdded} objects were added, ${cset.size} remain")
3 objects were added, 2 remain
*/
