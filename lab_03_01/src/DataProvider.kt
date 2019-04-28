interface DataProvider<in T1, out T2> {
    fun provide(param: T1? = null): T2
}