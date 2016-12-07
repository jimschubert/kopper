package kopper.typed

import org.testng.annotations.Test

import org.testng.Assert.*

class NumericArgumentTest {
    @Test
    fun `numeric should parse float arguments (short)`() {
        // Arrange
        val args = arrayOf("-r", "5.1")

        // Act
        val e = ExampleArgs(args)

        // Assert
        assertEquals(e.rate, 5.1f)
    }

    @Test
    fun `numeric should fall back to float defined defaults`() {
        // Arrange
        val args = arrayOf("-r")

        // Act
        val e = ExampleArgs(args)

        // Assert
        assertEquals(e.rate, 1.0f)
    }

    @Test
    fun `numeric should parse int arguments (short)`() {
        // Arrange
        val args = arrayOf("-m", "3")

        // Act
        val e = ExampleArgs(args)

        // Assert
        assertEquals(e.maxCount, 3)
    }

    @Test
    fun `numeric should parse int arguments (long)`() {
        // Arrange
        val args = arrayOf("--maxCount=300")

        // Act
        val e = ExampleArgs(args)

        // Assert
        assertEquals(e.maxCount, 300)
    }

    @Test
    fun `numeric should fall back to int defined defaults`() {
        // Arrange
        val args = arrayOf("-m")

        // Act
        val e = ExampleArgs(args)

        // Assert
        assertEquals(e.maxCount, 10)
    }
}