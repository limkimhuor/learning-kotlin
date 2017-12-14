package org.learning.ch04.initializingClass


// Initializing classes: primary constructor and initializer blocks

//class User constructor(_nickname: String) {
//    val nickname: String
//    init {
//        nickname = _nickname
//    }
//}

//class User(_nickname: String) {
//    val nickname = _nickname
//}

//class User(val nickname: String)

open class User(val nickname: String) {}
class TwitterUser(nickname: String) : User(nickname) {}

open class Button
class RadioButton: Button()
class Secretive private constructor() {} // If you want to ensure that your class can’t be instantiated by other code, you have to make the constructor private
/* other way
* class Secretive {
*   private constructor()
* */

//Secondary constructors

//open class View {
//    constructor(ctx: Context) {
//        // some code
//    }
//
//    constructor(ctx: Context, attr: AttributeSet) {
//        // some code
//    }
//
//}

//class MyButton : View {
//    constructor(ctx: Context) : super(ctx) {}
//    constructor(ctx: Context, attr: AttributeSet) : super(ctx, attr){}
//}
