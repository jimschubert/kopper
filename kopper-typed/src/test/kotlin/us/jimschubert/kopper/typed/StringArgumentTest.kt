package us.jimschubert.kopper.typed

import org.testng.annotations.Test

import org.testng.Assert.*

class StringArgumentTest {
    @Test
    fun `default text when not provided in args list`(){
        // Arrange
        val args = arrayOf<String>()

        // Act
        val e = ExampleDefaultStringArgs(args)

        // Assert
        assertEquals(e.first, "first")
    }

    @Test
    fun `default empty string when not provided in args list`(){
        // Arrange
        val args = arrayOf<String>()

        // Act
        val e = ExampleDefaultStringArgs(args)

        // Assert
        assertEquals(e.second, "")
    }

    @Test
    fun `argument without default is empty string when not provided in args list`(){
        // Arrange
        val args = arrayOf<String>()

        // Act
        val e = ExampleDefaultStringArgs(args)

        // Assert
        assertNotNull(e.third)
        assertEquals(e.third, "")
    }
}