package array_and_string.implementation

fun main(args: Array<String>) {

    val map = HashMap<String, String>()
    println("map.get(\"Test\") : " + map.get("Test"))
    map.put("Test", "Testing")
    println("map.get(\"Test\") : " + map.get("Test"))
    println("map.get(\"Testing\") : " + map.get("Testing"))
    println("map.size : " + map.size)

    map.put("Testing", "Test")
    println("map.get(\"Testing\") : " + map.get("Testing"))
    println("map.get(\"Test\") : " + map.get("Test"))
    println("map.get(\"test\") : " + map.get("test"))

    println("After Removing Xyz")
    map.remove("Xyz")
    println("map.get(\"Test\") : " + map.get("Test"))
    println("map.get(\"Testing\") : " + map.get("Testing"))

    println("After Removing Test")
    map.remove("Test")
    println("map.get(\"Test\") : " + map.get("Test"))
    println("map.get(\"Testing\") : " + map.get("Testing"))

    println("Testing Put operation")
    val languages = HashMap<String, String>()
    languages.put("K", "Kotlin")
    languages.put("J", "Java")
    languages.put("C", "C++")
    println("Getting C - ${languages.get("C")}")
    languages.put("C", "C#")
    println("Getting C After replacement - ${languages.get("C")}")
    languages.putIfAbsent("C", "C++")
    println("Getting C After replacement - ${languages.get("C")}")

    println()
    println("Testing constructor with Map Argument")
    val fruits = hashMapOf("A" to "Apple", "B" to "Banana", "C" to "Cherries")
    val moreFruites = HashMap(fruits)
    moreFruites.put("D", "Dates")
    println(moreFruites.get("A"))
    println(moreFruites.get("D"))
    println(moreFruites.get("M"))
    moreFruites.put("M", "Mango")
    println(moreFruites.get("M"))
    moreFruites.put("A", "Apricot")
    println(moreFruites.get("A"))
}