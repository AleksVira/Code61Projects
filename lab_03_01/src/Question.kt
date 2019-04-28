sealed class Question
class OneFromTwo(val questionText : String = "Q0", val variants: ArrayList<String> = arrayListOf("0")) : Question()
class OneFromMany(val questionText: String = "Q0", val variants: ArrayList<String> = arrayListOf("0")) : Question()
class TwoFromMany(val questionText: String = "Q0", val variants: ArrayList<String> = arrayListOf("0")) : Question()
class MathQuestion(val questionText: String = "Q0") : Question()
class ProverbQuestion(val questionText: String = "Q0") : Question()
class AnagramQuestion(val questionText: String = "Q0") : Question()
class MissingLetters(val questionText: String = "Q0") : Question()




