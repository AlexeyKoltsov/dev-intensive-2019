package ru.skillbranch.devintensive

import org.junit.Test
import ru.skillbranch.devintensive.extensions.*

import ru.skillbranch.devintensive.utils.Utils
import ru.skillbranch.devintensive.models.User
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

        println("""
            ${Utils.transliteration("Женя Стереотипов")} //Zhenya Stereotipov
            ${Utils.transliteration("Amazing Петр","_")} //Amazing_Petr
        """.trimIndent())

        println(Utils.transliteration("Иштымбеков Ахмадир Фулееман тан"))
    }

    @Test
    fun test_toInitials() {
        println("""
        ${Utils.toInitials("john" ,"doe")} -- JD
        ${Utils.toInitials("John", null)} -- J
        ${Utils.toInitials(null, null)} -- null
        ${Utils.toInitials(" ", "      ")} -- null
        ${Utils.toInitials("Пётр", "Иванов")} -- PI
        """)

    }

    @Test
    fun test_humanizeDiff() {
        val date = Date().add(-11,TimeUnits.SECOND)
        val diff = Date().humanizeDiff(date)

        println(diff)

        println("""
            ${Date().add(-2, TimeUnits.HOUR).humanizeDiff()} //2 часа назад
            ${Date().add(-5, TimeUnits.DAY).humanizeDiff()} //5 дней назад
            ${Date().add(2, TimeUnits.MINUTE).humanizeDiff()} //через 2 минуты
            ${Date().add(7, TimeUnits.DAY).humanizeDiff()} //через 7 дней
            ${Date().add(-400, TimeUnits.DAY).humanizeDiff()} //более года назад
            ${Date().add(400, TimeUnits.DAY).humanizeDiff()} //более чем через год
        """.trimIndent())
    }

    @Test
    fun user_Builder(){

        println("""
            ${User.Builder().id("6")
            .firstName("Иван")
            .lastName("Иванов")
            .avatar("av")
            .rating(78)
            .respect(34)
            .lastVisit(Date())
            .isOnline(true)
            .build()}
        """.trimIndent())
    }


    @Test
    fun test_plural(){
        println("""
            ${TimeUnits.SECOND.plural(5)} // 5 секунд
            ${TimeUnits.MINUTE.plural(1)} // 1 минута
            ${TimeUnits.SECOND.plural(1)} //1 секунду
            ${TimeUnits.MINUTE.plural(4)} //4 минуты
            ${TimeUnits.HOUR.plural(19)} //19 часов
            ${TimeUnits.DAY.plural(222)} //222 дня
        """.trimIndent())

    }

    @Test
    fun test_truncate(){
        println("""
        ${"Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate()} //Bender Bending R...
        ${"Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15)} //Bender Bending...
        ${"A     ".truncate(3)} //A
        """.trimIndent())

    }

    @Test
    fun test_strip(){
        println("""
            ${"<p class=\"title\">Образовательное IT-сообщество Skill Branch</p>".stripHtml()} //Образовательное IT-сообщество Skill Branch
            ${"<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml()}//Образовательное IT-сообщество Skill Branch
        """.trimIndent())

    }
}
