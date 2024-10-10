package com.dicoding.kotlin

import kotlin.random.Random

//fun main() {
//    val maxInt = Int.MAX_VALUE
//    val minInt = Int.MIN_VALUE
//    println(maxInt)
//    println(minInt)
//    val byteNumber: Byte = 10
//    val intNumber: Int = byteNumber.toInt()
//
//    val user = setUser("Firman",21)
//    println(user)
//
//    //name ande default argumen
//    val fullName = getFullName(middle = "is", first = "Kotlin", last = "Awesome")
//    println("$fullName \n")
//
//    //Varag (Variable Argumen
//    val number = sumNumbers(10,20,30,40,50)
//    println("Variable Argumen")
//    println(number)
//
//    //when in kotlin
//    val value = 51
//    val ranges = 10..50
//    when(value){
//        6 -> println("Value is 6")
//        7 -> println("Value is 7")
//        8 -> println("Value is 8")
//        else -> println("\nValue can't be reached\n")
//    }
//    when(value){
//        in ranges -> println("value is in the range\n")
//        !in ranges -> println("value is outside the range\n")
//    }
//
//    val registerNumber = when(val regis = getRegisterNumber()){
//        in 1..50 -> 50 * regis
//        in 51..100 -> 100 * regis
//        else -> regis
//    }
//
//    //when with is
//    val anyType : Any = 100L
//    when(anyType){
//        is Long -> println("the value has a Long type")
//        is String -> println("the value has a String type")
//        else -> println("undefined")
//    }
//
//    //while and do while
//    var counter = 1
//    while (counter<=7){
//        println("Hello World")
//        counter++
//    }
//
//    var counter2 = 1
//    do {
//        println("Hello, Firman")
//        counter2++
//    }while (counter2 <= 7)
//
//    //range with step
//    val rangeDown = 10 downTo 1
//    val rangeInt = 1..10 step 2
//    rangeInt.forEach{
//        print("$it")
//    }
//    println(rangeInt.step)
//
//    //break and continue
//    val listOfInt = listOf(1,2,3,null,7,9,null)
//    for (i in listOfInt){
//        if (i == null)  continue
//            print(i)
//    }
//    println("")
//    for (i in listOfInt ){
//        if (i == null) break
//        print(i)
//    }
//}
//
////name ande default argumen
//fun setUser(name: String, age: Int): String =
//    "Your name is $name, and your age is $age years old"
//
//fun getFullName(first: String, middle: String, last: String): String{
//    return "$first $middle $last"
//}
//
//fun sumNumbers(vararg number: Int): Int{
//    return number.size
//}
//
//fun getRegisterNumber() = Random.nextInt(100)

//OOP IN KOTLIN
//lateinit var nama: String
//fun main(){
//    val kucing = Animal()
//    println("Nama: ${kucing.name}, Berat: ${kucing.weight}, Umur: ${kucing.age}, Mamalia: ${kucing.isMamal}")
//    kucing.eat()
//    kucing.sleep()
//
//    //mengubah properti dari object kucing
//    kucing.name = "Oren"
//    kucing.weight = 4.00
//    kucing.age = 4
//    println("Nama: ${kucing.name}, Berat: ${kucing.weight}, Umur: ${kucing.age}, Mamalia: ${kucing.isMamal}")
//    kucing.eat()
//    kucing.sleep()
//
//    //Lateinit
//    nama = "Firman Ardiansyah"
//    if (::nama.isInitialized)
//    println(nama.length)
//    else
//        println("Not Initialized")
//
//    // lazy
//    val company: String by lazy { "Dicoding" }
//    println(company.length)
//}
//class Animal (){
//    var name: String = "Kucing"
//    var weight: Double = 3.2
//    var age: Int = 2
//    val isMamal: Boolean = true
//
//    fun eat() {
//        println("$name makan")
//    }
//    fun sleep(){
//        println("$name tidur")
//    }
//}