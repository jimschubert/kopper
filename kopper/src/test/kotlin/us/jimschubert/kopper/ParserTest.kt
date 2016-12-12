package us.jimschubert.kopper

import org.testng.annotations.Test

import org.testng.Assert.*
import org.testng.annotations.BeforeMethod

class ParserTest {
    var parser = Parser()
    @BeforeMethod
    fun before(){
        parser = Parser()
            .setName("Kopper")
            .setApplicationDescription("Kopper Tests")
            .flag("q", listOf("quiet", "silent"), description = "Run silently")
            .option("f", listOf("file"), description = "File name")
            .flag("a", listOf("allowEmpty"))
    }

    @Test
    fun `parses option with value by short text`() {
        // Arrange
        val filename = "asdf.txt"
        val args = arrayOf("-f", filename)

        // Act
        val arguments = parser.parse(args)

        // Assert
        assertEquals(arguments.option("f"), filename)
        assertEquals(arguments.option("file"), filename)
    }

    @Test
    fun `parses option without value by short text`() {
        // Arrange
        val args = arrayOf("-f")

        // Act
        val arguments = parser.parse(args)

        // Assert
        assertEquals(arguments.option("f"), null)
        assertEquals(arguments.option("file"), null)
    }

    @Test
    fun `parses option without value by long text`() {
        // Arrange
        val args = arrayOf("--file=")

        // Act
        val arguments = parser.parse(args)

        // Assert
        assertEquals(arguments.option("f"), null)
        assertEquals(arguments.option("file"), null)
    }

    @Test
    fun `parses flag by short text`() {
        // Arrange
        val args = arrayOf("-q")

        // Act
        val arguments = parser.parse(args)

        // Assert
        assertEquals(arguments.flag("q"), true)
        assertEquals(arguments.flag("quiet"), true)
        assertEquals(arguments.flag("silent"), true)
    }

    @Test
    fun `parses flag by long text`() {
        // Arrange
        val args = arrayOf("--quiet")

        // Act
        val arguments = parser.parse(args)

        // Assert
        assertEquals(arguments.flag("q"), true)
        assertEquals(arguments.flag("quiet"), true)
        assertEquals(arguments.flag("silent"), true)
    }

    @Test
    fun `parses flag by long text, last wins`() {
        // Arrange
        val args = arrayOf("--quiet", "--silent=false")

        // Act
        val arguments = parser.parse(args)

        // Assert
        assertEquals(arguments.flag("q"), false)
        assertEquals(arguments.flag("quiet"), false)
        assertEquals(arguments.flag("silent"), false)
    }

    @Test
    fun `parse multiple options and flags`() {
        // Arrange
        val filename = "asdf.txt"
        val args = arrayOf("-f", "asdf.txt", "--quiet=true", "--allowEmpty=false", "trailing", "arguments" )

        // Act
        val arguments = parser.parse(args)

        // Assert
        assertEquals(arguments.option("f"), filename)
        assertEquals(arguments.option("file"), filename)
        assertEquals(arguments.flag("q"), true)
        assertEquals(arguments.flag("quiet"), true)
        assertEquals(arguments.flag("silent"), true)
        assertEquals(arguments.flag("a"), false)
        assertEquals(arguments.flag("allowEmpty"), false)
        assertEquals(arguments.unparsedArgs, listOf("trailing", "arguments"))
    }
}