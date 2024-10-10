//package com.dicoding.kotlin
//
//import kotlin.reflect.KProperty
//
//fun main(){
//    val animal = Animal()
//    animal.name = "Kucing Oyen"
//    println("Nama Hewan: ${animal.name}")
//
//    val person = Person()
//    println("Nama Orang: ${person.name}")
//}
//class DelegateName {
//    private var value: Any = "Default"
//
//    operator fun getValue(classRef: Any?, property: KProperty<*>) : Any {
//        println("Fungsi ini sama seperti getter untuk properti ${property.name} pada class $classRef")
//        return value
//    }
//    operator fun setValue(classRef: Any?, property: KProperty<*>, newValue: Any){
//        println("Fungsi ini sama seperti setter untuk properti ${property.name} pada class $classRef")
//        println("Nilai ${property.name} dari: $value akan berubah menjadi $newValue")
//        value = newValue
//    }
//}
//class  Animal{
//    var name: Any by DelegateName()
//}
//class  Person{
//    var name: Any by DelegateName()
//}