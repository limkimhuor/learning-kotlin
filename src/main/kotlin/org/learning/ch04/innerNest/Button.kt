package org.learning.ch04.innerNest

// Inner and nested classes: nested by default
interface Serializable
interface State: Serializable
interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}

class Button : View {
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) {
    }

    class ButtonState : State {}
}

/*Correspondence between nested and inner classes in Java and Kotlin
* Class A declared within another class B                       |   In Java         |    In Kotlin
* Nested class (doesn’t store a reference to an outer class)    | static class A    |   class A
* Inner class (stores a reference to an outer class)            | class A           |   innerclass A
* */

class Outer {
    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}
