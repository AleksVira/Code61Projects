class SimpleDataProvider : DataProvider<Int, String> {
    override fun provide(param: Int?): String {
        val returnString = when (param) {
            null -> "0-0-0"
            else -> param.toString()
        }
        println("Provided data: $returnString")
        return returnString
    }
}