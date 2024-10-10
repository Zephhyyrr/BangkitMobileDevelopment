fun main(){
    // lIST
    var numberList : List<Int> = listOf(1,2,3,4,5)
    var listExample = listOf(2,2,'s',"Kotlin",7.2)
    val mutableList = mutableListOf(1,'a',"Firman",true,null)
    mutableList.add("JS")
    println(mutableList)

    // Set
    val integerSet = setOf(1,2,3,1,3,1,5,6,7)
    println(integerSet)

    val setA = setOf(1, 2, 4, 2, 1, 5)
    val setB = setOf(1, 2, 4, 5)

    val setC = setOf(1, 5, 7)
    val union = setA.union(setC)
    val intersect = setA.intersect(setC)

    println("Union : $union")
    println("Intersect : $intersect")

    // Map
    val capital = mapOf(
        "Jakarta" to "Indonesia",
        "London" to "England",
        "New Delhi" to "India"
    )

    println(capital["Jakarta"])
    val mutableCapital = capital.toMutableMap()

    mutableCapital.put("Amsterdam", "Netherlands")
    mutableCapital.put("Berlin", "Germany")

    println(mutableCapital)

    //Collection Operation
    val numberList2 = listOf(1,2,3,4,5,6,7,8,9,10)
    val listGenap = numberList2.filter { it % 2 == 0 }
    val multipliedBy5 = numberList.map { it * 5 }
    val kotlinChar = listOf('k', 'o', 't', 'l', 'i', 'n')
    val ascendingSort = kotlinChar.sorted()
    println(ascendingSort)
    println("Genap : $listGenap")
    println("Map : $multipliedBy5")
}