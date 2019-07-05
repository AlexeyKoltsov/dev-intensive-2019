package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?,String?>{
        var splitted: List<String>? = fullName?.split(" ")

        val firstName = splitted?.getOrNull(0)
        val lastName = splitted?.getOrNull(1)


        return firstName to lastName
    }

    fun transliteration(payload: String): String {
        var outstring:String = ""
        val baseDiff = 0x3CF

        val charArray = payload.toCharArray()
        for(ch in charArray){
            var diff:Int
            var converted:String = ""
            val notMirrored = "[ЖжЧчШшЩщЮюЯяЬьЪъЫы]"
            if(ch in Regex(notMirrored)){
                when(ch){
                    'Ж' -> converted = "Zh"
                    'ж' -> converted = "zh"
                    'Ч' -> converted = "Ch"
                    'ч' -> converted = "ch"
                    'Ш' -> converted = "Sh"
                    'ш' -> converted = "sh"
                    'Щ' -> converted = "Sch"
                    'щ' -> converted = "sch"
                    'Ю' -> converted = "Yu"
                    'ю' -> converted = "yu"
                    'Я' -> converted = "Ya"
                    'я' -> converted = "ya"
                    in Regex("[Ыы]") -> converted = "y"
                    in Regex("[ЬьЪъ]") -> converted = ""

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
        val fn = firstName ?: ""
        val ln = lastName ?: ""
        if(fn == "" || ln == ""){
            return ""
        }
        else {
            val first = transliteration(fn).toUpperCase().toCharArray()[0]
            val second = transliteration(ln).toUpperCase().toCharArray()[0]

            return "$first$second"
        }
    }

    operator fun Regex.contains(text: Char): Boolean = this.matches(text.toString())

}