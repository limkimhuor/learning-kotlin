package org.learning.ch04

import org.learning.ch04.innerNest.Button

/*
* Cover
* - Classes and interfaces
* - Nontrivial properties and constructors
* - Data classes and class delegation
* - Using the object keyword*/

fun main(args: Array<String>) {
    // Declare interface with a single abstract method name click().
//    val button = Button()
//    button.showOff()
//    button.setFocus(true)
//    button.click()
}

// Declare interface with a single abstract method name click().
interface Clickable {
    fun click()
    fun showOff() = println("I'm clickable!")
}

//class Button : Clickable {
//    override fun click() = println("I was clicked")
//}

interface Focusable {
    fun setFocus(b: Boolean) =
            println("I ${if (b) "got" else "lost"} focus.")
    fun showOff() = println("I'm focusable!")
}

/*What happens if you need to implement both interfaces in your class? Each of them
contains a showOff method with a default implementation; which implementation wins?
Neither one wins. Instead, you get the following compiler error if you don’t implement
showOff explicitly -> The Kotlin compiler forces you to provide your own implementation:
*/

class Button : Clickable, Focusable {
    override fun click() = println("I was clicked")
    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()

        // If you only need to invoke one inherited implementation
        // override fun showOff() = super<Clickable>.showOff()
    }
}

open class RichButton : Clickable { //This class is open: others can inherit from it.
    fun disable() {} //This function is "final": you can’t override it in a subclass
    open fun animate() {} // This function is open: you may override it in a subclass.
    override fun click() {} // This function overrides an open function and is open as well.

    /*Note that if you override a member of a base class or interface, the overriding
member will also be open by default. If you want to change this and forbid the subclasses
of your class from overriding your implementation, you can explicitly mark the
overriding member as final*/
//    final override fun click() {} // "final" isn’t redundant here because "override" without "final" implies being "open".`
}

abstract class Animated { //This class is abstract: you can’t create an instance of it.
    abstract fun animate() // This function is abstract: it doesn’t have an implementation and must be overridden in subclasses.
    open fun stopAnimating() { //Non-abstract functions in abstract classes aren’t open by default but can be marked as open.
    }
    fun animateTwice() { // Non-abstract functions in abstract classes aren’t open by default but can be marked as open.
    }
}

/* The meaning of access modifiers in a class
* Modifier  |   Corresponding member                    |   Comments
* ______________________________________________________________________________________________
* final     |    Can't be overridden                    |     Used by default for class members
* open      |    Can be overridden                      |     Should be specified explicitly
* abstract  |    Must be overridden                     |     Can be used only in abstract classes; abstract members can't have an implementation
* override  |    Overrides a memember in a super class  |     Overridden member is open by default, if not marked final
* */

/* Kotlin visibility modifiers
* Modifier          |   Class member        |   Top-level declaration
* ___________________________________________________________________
* public (default)  |  Visible everywhere   |    Visible everywhere
* internal          |  Visible in a module  |    Visible in a module
* protected         |  Visible in subclasses|       ---
* private           |  Visible in a class   |    Visible in a file
* */

internal open class TalktiveButton : Focusable {
    private fun yell()= println("Hey!")
    protected fun whisper() = println("Let's talk!")
}

//fun TalktiveButton.giveSpeech() {
//    yell()
//    whisper()
//}
/*
* Error:(93, 5) Kotlin: 'public' member exposes its 'internal' receiver type TalktiveButton
Error:(94, 5) Kotlin: Cannot access 'yell': it is private in 'TalktiveButton'
Error:(95, 5) Kotlin: Cannot access 'whisper': it is protected in 'TalktiveButton'
*/

// Summary
/*
- Interfaces in Kotlin are similar to Java’s but can contain default implementations and properties.
- All declarations are final and public by default.
- To make a declaration non- final , mark it as open .
- internal declarations are visible in the same module.
- Nested classes aren’t inner by default. Use the keyword inner to store a reference to an outer class.
- A sealed class can only have subclasses nested in its declaration.
- Initializer blocks and secondary constructors provide flexibility for initializing class instances.
- You use the field identifier to reference a property backing field from the accessor body.
- Data classes provide compiler-generated equals() , hashCode() , toString() , copy() , and other methods.
- Class delegation helps to avoid many similar delegating methods in your code.
- Object declaration is Kotlin’s way to define a singleton class.
- Companion objects (along with package-level functions and properties) replace Java’s static method and field definitions.
- Companion objects, like other objects, can implement interfaces or have extension functions or properties.
- Object expressions are Kotlin’s replacement for Java’s anonymous inner classes, with added power such as the ability
  to implement multiple interfaces and to modify the variables defined in the scope where the object is created.
*/
