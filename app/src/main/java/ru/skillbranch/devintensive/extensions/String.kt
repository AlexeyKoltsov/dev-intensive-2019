package ru.skillbranch.devintensive.extensions

fun String.truncate(number:Int=16):String{
    val len = this.trim().length

    if (len > number){
        return this.substring(0,number).trimEnd() + "..."
    }
    else{
        return this
    }

}

fun String.stripHtml():String{

    var res = this
    val ptnSpaces = """\s+"""
    val ptnHtml = """(<\/?[\w\s\d="'\.\\/;:-]*>)|(&#?[\w\d]+;)"""

    val step1 = Regex(ptnHtml)
    val step2 = Regex(ptnSpaces)
    res = step1.replace(res,"")
    println(res)
    res = step2.replace(res, " ")
    println(res)
    return res
}