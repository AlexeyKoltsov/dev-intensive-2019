package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    val lastVisit: Date? = null,
    val isOnline: Boolean = false
)
{
    constructor(id: String, firstName: String?, lastName: String?): this (
        id = id, firstName = firstName, lastName = lastName, avatar = null)

    private constructor(builder: Builder):this(
        builder.id,builder.firstName,builder.lastName,builder.avatar,builder.rating,builder.respect,builder.lastVisit,builder.isOnline)

    companion object Factory {
        private var lastId: Int = -1

        fun makeUser(fullName: String?): User {
            this.lastId++
            val (firstName, lastName) = Utils.parseFullName(fullName)

            //println("Created user:\nid:$lastId\nFirstname:$firstName\nLastname:$lastName")
            var user = User("$lastId", firstName, lastName)
            return user
        }

    }
         class Builder(){
            internal var id: String = "-1"
            internal var firstName: String? = null
            internal var lastName: String? = null
            internal var avatar: String? = null
            internal var rating: Int = 0
            internal var respect: Int = 0
            internal var lastVisit: Date? = null
            internal var isOnline: Boolean = false

            fun id(id: String) = apply { this.id = id }
            fun firstName(firstName: String?) = apply { this.firstName = firstName }
            fun lastName(lastName: String?) = apply { this.lastName = lastName }
            fun avatar(avatar: String?) = apply { this.avatar = avatar }
            fun rating(rating: Int) = apply { this.rating = rating }
            fun respect(respect: Int) = apply { this.respect = respect }
            fun lastVisit(lastVisit: Date?) = apply { this.lastVisit = lastVisit }
            fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }

            fun build() = User(this)

        }

}