package de.lathspell.test.service;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

@Slf4j
public class SpringUtilsTest {

    @Test
    public void testFileCopyUtils() throws Exception {
        String inStr;
        try (Reader in = new FileReader("src/test/resources/in.txt")) {
            inStr = FileCopyUtils.copyToString(in);
        }
        assertThat(inStr, is("in!"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertUitls() {
        FooService.getGreeting(null);
    }

    @Test
    public void testSystemPropertyUtils() {
        String x = System.getProperty("java.version");
        assertNotNull(x);
        String y = SystemPropertyUtils.resolvePlaceholders("${java.version}");
        assertNotNull(y);
    }

    @Test
    public void testAntPathMatcher() {
        AntPathMatcher apm = new AntPathMatcher();
        assertTrue(apm.match("/foo/**/*z", "/foo/bar/baz"));
        assertFalse(apm.match("/foo/**/*y", "/foo/bar/baz"));
    }

    @Test
    public void testStopWatch() throws Exception {
        StopWatch watch = new StopWatch("test");
        watch.start("sleeping");
        Thread.sleep(1000L * 1);
        watch.stop();
        assertThat(watch.getTotalTimeSeconds(), is(closeTo(1.0, 0.5)));
    }

    @Test
    public void testStreamUtils() throws Exception {
        String inStr;
        try (InputStream in = new FileInputStream("src/test/resources/in.txt")) {
            inStr = StreamUtils.copyToString(in, Charset.forName("UTF8"));
        }
        assertThat(inStr, is("in!"));
    }

    @Test
    public void testStringUtils() {
        // CSV
        List<String> list = Arrays.asList("Tim", "Tom", "Ts'o");
        String csv = StringUtils.collectionToDelimitedString(list, ",", "'", "'");
        assertThat(csv, is("'Tim','Tom','Ts'o'")); // WARNING: does not do escaping!

        // Trimming
        assertEquals("user input", StringUtils.trimWhitespace(" user input\t \n"));
    }

}
