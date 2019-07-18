package ru.skillbranch.devintensive.models

class Bender(var status:Status=Status.NORMAL, var question:Question=Question.NAME) {

    fun askQuestion():String = when(question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }


    fun listenAnswer(answer:String):Pair<String,Triple<Int,Int,Int>>{
        return if(question.answers.contains(answer.toLowerCase())){
            question = question.nextQuestion()
            //status = status.prevStatus()

            "Отлично - ты справился\n${question.question}" to status.color
            }
            else{
                status = status.nextStatus()
                var reaction = "Это неправильный ответ"

                when(status){
                    Status.CRITICAL -> reaction += ". Давай все по новой"
                    else -> reaction += "\n"
                }

                "$reaction${question.question}" to status.color
            }
    }

    enum class Status(val color:Triple<Int,Int,Int>){
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus():Status{
            return when{
                this.ordinal < values().lastIndex -> values()[this.ordinal+1]
                else -> values()[0]

            }

        }

        fun prevStatus():Status{
            return if(this.ordinal > 0){
                values()[this.ordinal - 1]
            }
            else {
                values().first()
            }
        }
    }


    enum class Question(val question: String, val answers: List<String>){
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun validate(answer: String): Boolean = answer.trim().firstOrNull()?.isUpperCase() ?: false
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик","bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validate(answer: String): Boolean = answer.trim().firstOrNull()?.isLowerCase() ?: false
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun validate(answer: String): Boolean = answer.trim().contains(Regex("\\d")).not()
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun validate(answer: String): Boolean = answer.trim().contains(Regex("^[0-9]*$"))
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): Boolean = answer.trim().contains(Regex("^[0-9]{7}$"))
        },
        IDLE("На этом всё, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): Boolean = true
        };

        abstract  fun nextQuestion():Question
        abstract fun validate(answer: String):Boolean
    }
}