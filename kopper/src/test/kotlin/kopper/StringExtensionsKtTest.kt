package kopper

import org.testng.annotations.Test

import org.testng.Assert.*

class StringExtensionsKtTest {
    @Test
    fun `test kvp starts with =`() {
        // Arrange
        val str = "=something"
        val expected = Pair("=something", null as String?)

        // Act
        val kvp = str.kvp()

        // Assert
        assertEquals(kvp, expected)
    }

    @Test
    fun `test kvp ends with =`() {
        // Arrange
        val str = "something="
        val expected = Pair("something", null as String?)

        // Act
        val kvp = str.kvp()

        // Assert
        assertEquals(kvp, expected)
    }

    @Test
    fun `test kvp contains =`() {
        // Arrange
        val key = "key"
        val value = "value"
        val str = "$key=$value"
        val expected = Pair(key,value)

        // Act
        val kvp = str.kvp()

        // Assert
        assertEquals(kvp, expected)
    }

    @Test
    fun `test kvp doesn't contain =`() {
        // Arrange
        val str = "keyonly"
        val expected = Pair(str, null as String?)

        // Act
        val kvp = str.kvp()

        // Assert
        assertEquals(kvp, expected)
    }
}