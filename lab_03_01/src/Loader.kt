import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

fun main() {

    val stringsData1 = arrayListOf(
        "khkhkh LKJHLKH oioisjlkj 1212    asd asd .... asd asaaaaasfghfg",
        " sdfdfdsf sdfasdfasdfasdf asdfasdfa sdf asdf 1232ewdasizhkjn",
        "./,., dfsd sdf sfdghldkzxc rtytlgfdkj;n;lm;l, dhsljf67sdfSD",
        ".sdfsdf 654sdjlhfhkjsndf/ gsdfgfd5vxc465v4654v zgvxzgfzfxvc",
        "sdgdf gdfgxfcgfdgv 3z54v68a4se5d43f1 z"
    )

    for (i in 0..stringsData1.size - 1) {
        println(stringsData1[i].split(" ").maxBy { it.length })
    }

    printMinusLine()

    fun maxNumberInString(number: Int): Char? {
        return number.toString().toCharArray().max()
    }

    for (i in 0..10) {
        val newInt = Random.nextInt(1_000_000)
        println("int = $newInt, maxNumber = ${maxNumberInString(newInt)}")
    }

    printMinusLine()

    var symmetricCounter = 0
    for (hours in 0..23) {
        for (minutes in 0..59) {
            val hoursText = "%02d".format(hours)
            val minutesText = "%02d".format(minutes)
            if (hoursText.equals(minutesText.reversed())) {
                println("Symmetric Time: $hoursText:$minutesText")
                symmetricCounter++
            }
        }
    }
    println("Total symmetrical = $symmetricCounter")

    printMinusLine()

    val testList = listOf("A", "B", "C", "D")
    println(testList.toMapConverter())

    printMinusLine()

    val currentDate = Date()
    println(currentDate.myFormattedString())

    printMinusLine()

    val testHashMap = hashMapOf("key11" to "val11", "key22" to "val22", "key33" to "val33", "key44" to "val44")
    println(testHashMap.allKeys())

    printMinusLine()

    println("DataProvider implementation")
    val provider1 = SimpleDataProvider()
    provider1.provide()
    val provider2 = SimpleDataProvider()
    provider2.provide(152)

    printMinusLine()

    fun findAnswer(question: Question): String = when (question) {
        is OneFromTwo -> "Question: ${question.questionText}. I’m only one answer: ${question.variants[0]} or ${question.variants[1]}"
        is OneFromMany -> "Question: ${question.questionText}. I’m only one answer from many versions: ${question.variants}"
        is TwoFromMany -> "Question: ${question.questionText}. I have two answers from ${question.variants}"
        is MathQuestion -> "Question: ${question.questionText}. I’m hard math answer"
        is ProverbQuestion -> "Question: ${question.questionText}. I’m cool PROVERB"
        is AnagramQuestion -> "Question: ${question.questionText}. I’m ANAGRAM"
        is MissingLetters -> "Question: ${question.questionText}. I’m word without missing letters"
    }

    println("Sealed class example")
    val questions: ArrayList<Question> = ArrayList()
    questions.add(OneFromTwo(questionText = "Question 1", variants = arrayListOf("1", "2")))
    questions.add(OneFromMany(questionText = "Question 2", variants = arrayListOf("1", "2", "3", "4")))
    questions.add(TwoFromMany(questionText = "Question 3", variants = arrayListOf("1", "2", "3", "4", "5")))
    questions.add(MathQuestion(questionText = "Question 4"))
    questions.add(ProverbQuestion(questionText = "Question 6"))
    questions.add(AnagramQuestion(questionText = "Question 7"))
    questions.add(MissingLetters(questionText = "Question 8"))

    questions.forEach {
        println(findAnswer(it))
    }
}

private fun printMinusLine() {
    println("-----------------------")
}