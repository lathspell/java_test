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

        mockMvc.perform(put("/api/city/cologne/temp/43")).andExpect(status().isOk)
        mockMvc.perform(put("/api/city/berlin/temp/34")).andExpect(status().isOk)
        mockMvc.perform(put("/api/city/cologne/temp/27")).andExpect(status().isOk)

        val resultJson = mockMvc.perform(get("/api/cities")).andExpect(status().isOk).andReturn().response.contentAsString
        val actual: Map<String, String> = ObjectMapper().readValue(resultJson)

        val expected = mapOf("cologne" to "27", "berlin" to "34")
        assertEquals(expected, actual)
    }

}
