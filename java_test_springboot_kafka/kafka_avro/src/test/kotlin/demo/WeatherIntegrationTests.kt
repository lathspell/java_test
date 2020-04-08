package demo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/** All tests use different Kafka data types to store basically String,Int tuples. */
@SpringBootTest
@AutoConfigureMockMvc
class WeatherIntegrationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    /** The Kafka `city_temp` topic uses String,String as data types. */
    @Test
    fun `city temperatures`() {
        val expected = mapOf("cologne" to "27", "berlin" to "34")

        mockMvc.perform(put("/api/city/cologne/temp/43")).andExpect(status().isOk)
        mockMvc.perform(put("/api/city/berlin/temp/34")).andExpect(status().isOk)
        mockMvc.perform(put("/api/city/cologne/temp/27")).andExpect(status().isOk)

        Thread.sleep(3 * 1000)

        val mvcResult = mockMvc.perform(get("/api/cities/temp")).andExpect(status().isOk).andReturn()
        val actual: Map<String, String> = ObjectMapper().readValue(mvcResult.response.contentAsString)

        assertEquals(expected, actual)
    }

    /** The Kafka `city_temp` topic uses String,CityRain as data types and classes generated from src/main/avro definitions. */
    @Test
    fun `city rain`() {
        val expected = mapOf("cologne" to "27", "berlin" to "34")

        mockMvc.perform(put("/api/city/cologne/rain/43")).andExpect(status().isOk)
        mockMvc.perform(put("/api/city/berlin/rain/34")).andExpect(status().isOk)
        mockMvc.perform(put("/api/city/cologne/rain/27")).andExpect(status().isOk)

        Thread.sleep(3 * 1000)

        val mvcResult = mockMvc.perform(get("/api/cities/rain")).andExpect(status().isOk).andReturn()
        val actual: Map<String, String> = ObjectMapper().readValue(mvcResult.response.contentAsString)

        assertEquals(expected, actual)
    }
}
