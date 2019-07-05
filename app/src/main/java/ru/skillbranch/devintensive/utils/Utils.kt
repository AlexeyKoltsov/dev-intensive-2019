package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?,String?>{
        var splitted: List<String>? = fullName?.split(" ")

        val firstName = splitted?.getOrNull(0)
        val lastName = splitted?.getOrNull(1)


        return firstName to lastName
    }

    fun transliteration(payload: String): String {

        val charArray = payload.toCharArray()
        for(ch in charArray){
            println(ch)
        }

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}