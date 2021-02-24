package array_and_string.isUnique

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class IsUniqueUnitTest {

    @Test
    fun testIsUniqueEmptyString() {
        assertEquals(true, isUniqueBruteForce(""))
        assertEquals(true, isUniqueCharSet(""))
    }

    @Test
    fun testIsUniqueUnique(){
        assertEquals(true, isUniqueBruteForce("pedram"))
        assertEquals(true, isUniqueCharSet("pedram"))
    }

    @Test
    fun testIsUniqueNonUnique(){
        assertEquals(false, isUniqueBruteForce("test"))
        assertEquals(false, isUniqueCharSet("test"))
    }

    @Test
    fun testIsUniqueNotUniqueAtEnd(){
        assertEquals(false, isUniqueBruteForce("estt"))
        assertEquals(false, isUniqueCharSet("estt"))
    }

}