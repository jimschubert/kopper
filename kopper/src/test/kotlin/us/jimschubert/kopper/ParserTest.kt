package us.jimschubert.kopper

import org.testng.annotations.Test

import org.testng.Assert.*
import org.testng.annotations.BeforeMethod

class ParserTest {
    var parser = Parser()
    @BeforeMethod
    fun before(){
        parser = Parser()

        parser.name = "Kopper"
        parser.applicationDescription = "Kopper Tests"

        parser.flag("q", listOf("quiet", "silent"), description = "Run silently")
        parser.option("f", listOf("file"), description = "File name")
        parser.flag("a", listOf("allowEmpty"))
    }

    @Test
    fun `parses option with value by short text`() {
        // Arrange
        val filename = "asdf.txt"
        val args = arrayOf("-f", filename)

        // Act
        parser.parse(args)

        // Assert
        assertEquals(parser.get("f"), filename)
        assertEquals(parser.get("file"), filename)
    }

    @Test
    fun `parses option without value by short text`() {
        // Arrange
        val args = arrayOf("-f")

        // Act
        parser.parse(args)

        // Assert
        assertEquals(parser.get("f"), null)
        assertEquals(parser.get("file"), null)
    }

    @Test
    fun `parses option without value by long text`() {
        // Arrange
        val args = arrayOf("--file=")

        // Act
        parser.parse(args)

        // Assert
        assertEquals(parser.get("f"), null)
        assertEquals(parser.get("file"), null)
    }

    @Test
    fun `parses flag by short text`() {
        // Arrange
        val args = arrayOf("-q")

        // Act
        parser.parse(args)

        // Assert
        assertEquals(parser.isSet("q"), true)
        assertEquals(parser.isSet("quiet"), true)
        assertEquals(parser.isSet("silent"), true)
    }

    @Test
    fun `parses flag by long text`() {
        // Arrange
        val args = arrayOf("--quiet")

        // Act
        parser.parse(args)

        // Assert
        assertEquals(parser.isSet("q"), true)
        assertEquals(parser.isSet("quiet"), true)
        assertEquals(parser.isSet("silent"), true)
    }

    @Test
    fun `parses flag by long text, last wins`() {
        // Arrange
        val args = arrayOf("--quiet", "--silent=false")

        // Act
        parser.parse(args)

        // Assert
        assertEquals(parser.isSet("q"), false)
        assertEquals(parser.isSet("quiet"), false)
        assertEquals(parser.isSet("silent"), false)
    }

    @Test
    fun `parse multiple options and flags`() {
        // Arrange
        val filename = "asdf.txt"
        val args = arrayOf("-f", "asdf.txt", "--quiet=true", "--allowEmpty=false", "trailing", "arguments" )

        // Act
        parser.parse(args)

        // Assert
        assertEquals(parser.get("f"), filename)
        assertEquals(parser.get("file"), filename)
        assertEquals(parser.isSet("q"), true)
        assertEquals(parser.isSet("quiet"), true)
        assertEquals(parser.isSet("silent"), true)
        assertEquals(parser.isSet("a"), false)
        assertEquals(parser.isSet("allowEmpty"), false)
        assertEquals(parser.remainingArgs, listOf("trailing", "arguments"))
    }
}