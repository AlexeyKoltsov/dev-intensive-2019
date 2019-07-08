package ru.skillbranch.devintensive.extensions

fun String.truncate(number:Int=16):String{
    val str = this.trim()
    val len = str.length

    if (len > number){
        return str.substring(0,number).trimEnd() + "..."
    }
    else{
        return str
    }
}

fun String.stripHtml():String{

    val step1 = Regex("(<.*?>)|(&#?[\\w\\d]+;)")
    val step2 = Regex(" {2,}")

    return this.replace(step1,"").replace(step2," ")
}