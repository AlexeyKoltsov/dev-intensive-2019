package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.utils.Utils.contains

object Utils {
    fun parseFullName(fullName:String?):Pair<String?,String?>{
        var splitted: List<String>? = fullName?.trim()?.split(" ")

        var firstName = splitted?.getOrNull(0)
        var lastName = splitted?.getOrNull(1)

        if(firstName == "") firstName = null
        if(lastName == "") lastName = null

        return firstName to lastName
    }

    fun transliteration(payload: String, divider:String=" "): String {
        var outstring:String = ""
        val baseDiff = 0x3CF

        val charArray = payload.toCharArray()
        for(ch in charArray){
            var diff:Int
            var converted:String = ""
            val notMirrored = "[ЖжЧчШшЩщЮюЯяЬьЪъЫы ]"
            val latinChars = "[A-Za-z]"

            if(ch in Regex(latinChars)){
                converted = ch.toString()
            }
            else if(ch in Regex(notMirrored)){
                when(ch){
                    'Ж' -> converted = "Zh"
                    'ж' -> converted = "zh"
                    'Ч' -> converted = "Ch"
                    'ч' -> converted = "ch"
                    in Regex("[ШЩ]") -> converted = "Sh"
                    in Regex("[шщ]") -> converted = "sh"
                    'Ю' -> converted = "Yu"
                    'ю' -> converted = "yu"
                    'Я' -> converted = "Ya"
                    'я' -> converted = "ya"
                    in Regex("[Ыы]") -> converted = "i"
                    in Regex("[ЬьЪъ]") -> converted = ""
                    ' ' -> converted = divider
                    else -> throw(IllegalArgumentException("The character $ch unsupported here"))
                }
            }
            else {

                when (ch) {
                    in Regex("\\s") -> diff = 0
                    in Regex("[ВвЁ]") -> diff = baseDiff - 19
                    in Regex("[Гг]") -> diff = baseDiff - 3
                    in Regex("[ДдЕеЙй]") -> diff = baseDiff + 1
                    in Regex("[ё]") -> diff = 1004
                    in Regex("[Зз]") -> diff = baseDiff - 18
                    in Regex("[РрСсТтУу]") -> diff = baseDiff - 1
                    in Regex("[Фф]") -> diff = baseDiff + 15
                    in Regex("[Хх]") -> diff = baseDiff + 14
                    in Regex("[Цц]") -> diff = baseDiff + 20
                    in Regex("[Ээ]") -> diff = baseDiff + 25
                    else -> diff = baseDiff
                }
                converted = ch.minus(diff).toString()
            }


            outstring += converted
        }

        return outstring
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val first:String
        val second:String

        var fn = firstName?.trim() ?: ""
        var ln = lastName?.trim() ?: ""

        if(fn != ""){
            first = fn.toUpperCase().toCharArray()[0].toString()
        }
        else{
            first = ""
        }
        if(ln != ""){
            second = ln.toUpperCase().toCharArray()[0].toString()
        }
        else{
            second = ""
        }
        return if (first == "" && second == "") null else "$first$second"

    }

    public operator fun Regex.contains(text: Char): Boolean = this.matches(text.toString())
    public operator fun Regex.contains(text: String): Boolean = this.matches(text)

}