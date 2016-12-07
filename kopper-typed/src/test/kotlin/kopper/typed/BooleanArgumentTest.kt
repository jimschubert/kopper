package kopper.typed

import org.testng.annotations.Test

import org.testng.Assert.*

class BooleanArgumentTest {
    @Test
    fun `boolean short argument provided(switch)`() {
        // Arrange
        val args = arrayOf("-q")

        // Act
        val e = ExampleArgs(args)

        // Assert
        assertTrue(e.quiet)
    }

    @Test
    fun `boolean short argument with explicit value provided`() {
        // Arrange
        val args = arrayOf("-q", "false")

        // Act
        val e = ExampleArgs(args)

        // Assert
        assertFalse(e.quiet)
    }

    @Test
    fun `boolean long argument with explicit false value provided`() {
        // Arrange
        val args = arrayOf("--quiet=false")

        // Act
        val e = ExampleArgs(args)

        // Assert
        assertFalse(e.quiet)
    }

    @Test
    fun `boolean long argument with explicit true value provided`() {
        // Arrange
        val args = arrayOf("--quiet=true")

        // Act
        val e = ExampleArgs(args)

        // Assert
        assertTrue(e.quiet)
    }
}