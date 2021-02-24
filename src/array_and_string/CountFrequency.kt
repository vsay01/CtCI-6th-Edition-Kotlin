package array_and_string

/*Problem: count the frequency of all the characters in String S*/
fun main(args: Array<String>) {
    //countFreq("ababcd")
    countFreqHashFunction("ababcd")
}

/* Without using hash function ; Run time for this is O(26 N) */
fun countFreq(string: String) {
    //0..10 ; 0 to 10
    //0 until 10 ; 0 to 9

    ('a'..'z').forEach { ch ->
        var count = 0
        for (len in string.indices) {
            if(string[len] == ch)
                count++
        }
        println("$ch : $count")
    }
}

/* With hash function; Run time O(N) */
private fun hashFunction(c: Char) = (c - 'a')

fun countFreqHashFunction(string: String) {
    val freq = IntArray(26)
    for (len in string.indices) {
        val index = hashFunction(string[len])
        freq[index]++
    }

    for (counter in 0..25) {
        println((counter + 'a'.toInt()).toChar() + " : " + freq[counter])
    }
}

