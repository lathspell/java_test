package demo

import demo.kafka.generated.Color
import demo.kafka.generated.Foo
import org.apache.avro.file.DataFileReader
import org.apache.avro.file.DataFileWriter
import org.apache.avro.specific.SpecificDatumReader
import org.apache.avro.specific.SpecificDatumWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File
import java.math.BigDecimal
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import java.util.concurrent.TimeUnit

class AvroTests {

    @Test
    fun `avro serializing`() {
        // some are a bit tricky, see `org.apache.avro.Conversions` for hints
        val foo = Foo.newBuilder()
                .setCity("Cologne")
                .setSmallNum(42)
                .setBigNum(452342342342L)
                .setDesired(true)
                .setColor(Color.RED)
                .setPrice(ByteBuffer.wrap(BigDecimal("3.14").unscaledValue().toByteArray()))
                .setUuid(UUID.fromString("a7f9baaa-79e3-11ea-b91b-dfb6caee3f0a").toString())
                .setCreatedAtDate(LocalDate.parse("2020-04-01").toEpochDay().toInt())
                .setCreatedAtTimeUs(0)
                .setCreatedAtTimeUs(TimeUnit.NANOSECONDS.toMicros(LocalTime.parse("12:00:42.1234568").toNanoOfDay()))
                .build()

        // write
        val outFile = File("src/test/resources/foo.out")
        val fooDatumWriter = SpecificDatumWriter(Foo::class.java)
        val dataFileWriter = DataFileWriter(fooDatumWriter)
        dataFileWriter.create(foo.schema, outFile)
        dataFileWriter.append(foo)
        dataFileWriter.close()

        val expectedBinary = Files.readAllBytes(Path.of("src/test/resources/foo.ok"))
        val actualBinary = Files.readAllBytes(Path.of("src/test/resources/foo.out"))
        // assertEquals(expectedBinary.toList(), actualBinary.toList()) - Binary contents differ!

        // reread
        val fooDatumReader = SpecificDatumReader(Foo::class.java)
        val dataFileReader = DataFileReader(File("src/test/resources/foo.out"), fooDatumReader)
        assertTrue(dataFileReader.hasNext())
        val fooReread = dataFileReader.next()
        dataFileReader.close()

        assertEquals(foo, fooReread)
    }
}
