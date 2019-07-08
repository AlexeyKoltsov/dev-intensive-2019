package ru.skillbranch.devintensive.models

import android.service.voice.AlwaysOnHotwordDetector
import java.util.*

abstract class BaseMessage(
    val id:String,
    val from:User,
    val chat:Chat,
    val isIncoming:Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage():String

    companion object AbstractMFactory{
        var lastid = -1
        fun makeMessage(from:User,chat: Chat,date: Date=Date(),type:String="text",payload: Any?,isIncoming: Boolean=false):BaseMessage{
            lastid++
            return when(type){
                "image" -> ImageMessage("$lastid",from,chat,date = date,image = payload as String,isIncoming = isIncoming)
                else -> TextMessage("$lastid",from,chat,date = date,text = payload as String,isIncoming = isIncoming)
            }
        }
    }
}