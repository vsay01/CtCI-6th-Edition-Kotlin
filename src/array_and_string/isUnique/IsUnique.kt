package array_and_string.isUnique

fun main(args: Array<String>) {
    //println("isUniqueBruteForce(abc) : " + isUniqueBruteForce("abc"))
    //println("isUniqueBruteForce(abcc) : " + isUniqueBruteForce("abcc"))
    println("isUniqueCharSet(abcc) : " + isUniqueCharSet("abc"))
}

/*
"abcc"
--> a == b ? a == c ? a == c ?
--> b == c ? b == c ?
--> c == c ?

Time complexity = O(n^2)
Space complexity = O(1)
* */
fun isUniqueBruteForce(string: String): Boolean {
    var isUnique = true
    for(counter in string.indices) {
        println("counter = $counter")
        for(ch in counter + 1 until string.length) {
            println("ch = $ch")
            println("string[counter] / string[ch] : " + string[counter] + "/" + string[ch])
            if(string[counter] == string[ch]) {
                isUnique = false
                break
            }
        }
    }
    return isUnique
}

/*
* "pedram"
* boolean array (128) --> all false values by default
*
* Time Complexity = O(n)
* Space complexity = O(1)
* */
fun isUniqueCharSet(str: String) : Boolean {
    //you can't form a string of 280 unique characters out of a 128-character alphabet.
    if (str.length > 128) return false

    val charSet = BooleanArray(128)

    for(index in str.indices) {
        val value = str[index].toInt() //return int value of the ASCII; a = 97, b = 98, ....
        println(value)
        if(charSet[value]) { //Already found this char in string
            return false
        }

        charSet[value] = true
    }
    return true
}