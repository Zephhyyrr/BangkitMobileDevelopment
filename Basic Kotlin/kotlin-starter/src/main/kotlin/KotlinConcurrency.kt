//// Coroutines
//import kotlinx.coroutines.*
//
//fun main() = runBlocking{
////    launch {
////        delay(1000L)
////        println("Coroutines!")
////    }
////    println("Hello,")
////    delay(2000L)
//    val capital = async { getCapital() }
//    val income = async { getIncome() }
//    println("Your profit is ${income.await() - capital.await()}")
//}
//suspend fun getCapital(): Int{
//    delay(1000L)
//    return 50000
//}
//suspend fun getIncome(): Int {
//    delay(1000L)
//    return 75000
//}
//
fun main(){
    for (i in 1..3) {
        for (j in 1..i) {
            print(j)
        }
    }
}