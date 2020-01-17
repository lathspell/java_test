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

@SpringBootTest
@AutoConfigureMockMvc
class WeatherIntegrationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `update weather for city`() {
        val expected = mapOf("cologne" to "27", "berlin" to "34")

        Thread.sleep(3 * 1000) // Wait some time for the Consumer to register with the Kafka server

        mockMvc.perform(put("/api/city/cologne/temp/43")).andExpect(status().isOk)
        mockMvc.perform(put("/api/city/berlin/temp/34")).andExpect(status().isOk)
        mockMvc.perform(put("/api/city/cologne/temp/27")).andExpect(status().isOk)

        val mvcResult = mockMvc.perform(get("/api/cities")).andExpect(status().isOk).andReturn()
        val actual: Map<String, String> = ObjectMapper().readValue(mvcResult.response.contentAsString)

        assertEquals(expected, actual)
    }

}
