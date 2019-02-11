package de.lathspell.test;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import de.lathspell.test.springboot.Application;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
@RunWith(SpringRunner.class)
public class WeatherTest {

    @Autowired
    private TestRestTemplate client;

    /**
     * Check for Prometheus compatible output.
     *
     * <pre>
     * # HELP Temperature_C Current garden temperature
     * # TYPE Temperature_C gauge
     * Temperature_C{location="garden",} 38.0
     * </pre>
     */
    @Test
    public void testPrometheusExport() {
        String output = client.getForObject("/actuator/prometheus", String.class);
        assertThat(output).contains("# HELP Temperature_C Current garden temperature");
        assertThat(output).contains("# TYPE Temperature_C gauge");
        assertThat(output).matches(Pattern.compile(".*Temperature_C\\{location=\"garden\",\\} \\d+\\.\\d+.*", Pattern.DOTALL));
        assertThat(output).contains("# TYPE WeatherChanges_total counter");
        assertThat(output).contains("# TYPE Thunders_total counter");
    }

    @Test
    public void testCounters() throws InterruptedException {
        Thread.sleep(300L);
        
        String output = client.getForObject("/actuator/prometheus", String.class);
        double c1 = Arrays.stream(output.split("\n")).filter(it -> it.matches("^Thunders_total .*")).map(it -> it.replaceFirst("^.* ", "")).mapToDouble(Double::new).findFirst().getAsDouble();
        
        Thread.sleep(300L);

        output = client.getForObject("/actuator/prometheus", String.class);
        double c2 = Arrays.stream(output.split("\n")).filter(it -> it.matches("^Thunders_total .*")).map(it -> it.replaceFirst("^.* ", "")).mapToDouble(Double::new).findFirst().getAsDouble();
        
        assertThat(c1).isGreaterThan(0.0);
        assertThat(c2).isGreaterThan(c1);
    }
}
