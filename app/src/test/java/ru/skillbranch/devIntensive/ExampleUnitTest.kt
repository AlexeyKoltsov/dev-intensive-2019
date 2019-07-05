package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.TimeUnit
import ru.skillbranch.devintensive.extensions.add
import ru.skillbranch.devintensive.utils.Utils
import ru.skillbranch.devintensive.extensions.humanizeDiff
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun test_transliteration(){
        var string = "Аа Бб Вв Гг Дд Ее Ёё Жж Зз Ии Йй Кк Лл Мм Нн Оо Пп Рр Сс Тт Уу Фф Хх Цц Чч Шш Щщ Ьь Ыы Ъъ Ээ Юю Яя"
        var res = Utils.transliteration(string)
        println(string)
        println(res)

        println(Utils.transliteration("Иштымбеков Ахмадир Фулееман тан"))
    }

    @Test
    fun test_toInitials() {
        var fn = null
        var ln = "Петров"

        val res = Utils.toInitials(fn,ln)
        println(res)
    }

    @Test
    fun test_humanizeDiff() {
        val date = Date().add(-11,TimeUnit.SECOND)
        val diff = Date().humanizeDiff(date)

        println(diff)
    }


}
