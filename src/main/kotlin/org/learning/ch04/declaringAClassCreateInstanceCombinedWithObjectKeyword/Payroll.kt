package org.learning.ch04.declaringAClassCreateInstanceCombinedWithObjectKeyword

import org.learning.ch02.Person
import java.io.File

object Payroll {
    val allEmployees = arrayListOf<Person>()

    fun calculateSalary() {
        for (person in allEmployees) {}
    }
}

/*
Payroll.allEmployees.add(Person(...))
Payroll.calculateSalary()
*/

object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(file1: File, file2: File): Int {
        return file1.getPath().compareTo(file2.getPath(),
                ignoreCase = true)
    }
}
/*
println(CaseInsensitiveFileComparator.compare(File("/User"), File("/user"))
0
*/

/*
>>> val files = listOf(File("/Z"), File("/a"))
>>> println(files.sortedWith(CaseInsensitiveFileComparator))
[/a, /Z]
*/

// Companion objects: a place for factory methods and static members
class A {
    companion object {
        fun bar() {
            println("Companion object called")
        }
    }
}
/*
>>> A.bar()
Companion object called
*/

//class User {
//    val nickname: String
//    constructor(email: String) { //Secondary constructors
//        nickname = email.substringBefore('@')
//    }
//
//    constructor(facebookAccountId: Int) { //Secondary constructors
//        nickname = getFacebookName(facebookAccountId)
//    }
//    fun getFacebookName(accountId: Int) = "123ABC"
//}
// use factory method
class User(val nickname: String) {
    companion object {                                      //Declares the companion object
        fun newSubscribingUser(email: String) =         //Factory method creating a new user by email
                User(email.substringBefore('@'))
        fun newFacebookUser(accountId: Int) =            //Factory method creating a new user by Facebook account ID
                User(getFacebookName(accountId))
    }
}
fun getFacebookName(accountId: Int) = "123ABC"

/*
>>> val subscribingUser = User.newSubscribingUser("bob@gmail.com")
>>> val facebookUser = User.newFacebookUser(4)
>>> println(subscribingUser.nickname)
bob
*/

/*
class Person(val name: String) {
    companion object Loader {
        fun fromJSON(jsonText: String): Person = ...
    }
}
>>> person = Person.Loader.fromJSON("{name: 'Dmitry'}")
>>> person.name
Dmitry
>>> person2 = Person.fromJSON("{name: 'Brent'}")
>>> person2.name
Brent
*/

// IMPLEMENTING INTERFACES IN COMPANION OBJECTS
