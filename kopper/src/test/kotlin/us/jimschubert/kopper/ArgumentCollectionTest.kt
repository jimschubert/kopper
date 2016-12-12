package us.jimschubert.kopper

import org.testng.Assert.*
import org.testng.annotations.Test

class ArgumentCollectionTest {
    @Test
    fun `ArgumentCollection should act like a collection`(){
        // Arrange
        val arguments = listOf(
                Argument(true, BooleanOption("a")),
                Argument(false, BooleanOption("b")),
                Argument(true, BooleanOption("ab")),
                Argument(false, BooleanOption("c"))
        )
        val argumentCollection = ArgumentCollection(listOf(), arguments)

        // Act
        val trues = argumentCollection.filter { arg -> arg.value == true }

        // Assert
        assertEquals(trues.size, 2)
    }

    @Test
    fun `ArgumentCollection should offer helpers for options and flags`(){
        // Arrange
        val arguments = listOf(
                Argument(true, BooleanOption("a")),
                Argument("some_name", StringOption("name")),
                Argument(false, BooleanOption("z"))
        )
        val argumentCollection = ArgumentCollection(listOf(), arguments)

        // Act
        val a = argumentCollection.flag("a")
        val name = argumentCollection.option("name")
        val z = argumentCollection.flag("z")

        // Assert
        assertTrue(a)
        assertEquals(name, "some_name")
        assertFalse(z)
    }
}