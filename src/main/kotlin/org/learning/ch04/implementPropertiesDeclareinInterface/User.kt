package org.learning.ch04.implementPropertiesDeclareinInterface

interface User {
    val nickname: String
}
class PrivateUser(override val nickname: String) : User

class SubscribingUser(val email: String) : User {
    override val nickname: String
        get() = email.substringBefore('@')
}
class FacebookUser(val accountId: Int) : User {
    override val nickname = getFacebookName(accountId)
    fun getFacebookName(accountId: Int) = "123ABC"
}

//interface User {
//    val email: String
//    val nickname: String
//        get() = email.substringBefore('@')
//}


