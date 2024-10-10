package com.dicoding.oop
import com.dicoding.oop.utils.*

fun main(){
    sayHello()
    println(factorial(4.0))
    println(pow(3.0, 2.0))
    println(PI)
    println(areaOfCircle(14.0))

    val someStringValue: String? = "12"
    var someIntValue: Int = 0

    try {
        someIntValue = someStringValue!!.toInt()
    } catch (e: NullPointerException) {
        someIntValue = 0
    } catch (e: NumberFormatException) {
        someIntValue = -1
    } finally {
        when(someIntValue){
            0 -> println("Catch block NullPointerException terpanggil !")
            -1 -> println("Catch block NumberFormatException terpanggil !")
            else -> println(someIntValue)
        }
    }
}