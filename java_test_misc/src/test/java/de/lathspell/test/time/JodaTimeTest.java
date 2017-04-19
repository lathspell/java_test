package de.lathspell.test.time;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// Deutsche Locales vorausgesetzt!
public class JodaTimeTest<BasePeriod> {

    private static final DateTimeFormatter isodate = ISODateTimeFormat.date();

    @Test
    public void properties() {
        DateTime datetime = new DateTime(2009, 1, 29, 23, 01, 00, 00);

        // Properties als int
        assertEquals(datetime.getYear(), 2009);
        assertEquals(datetime.getMonthOfYear(), 1);
        assertEquals(datetime.getDayOfMonth(), 29);
        assertTrue(datetime.isBefore(null));

        // Property Objekte
        assertEquals(datetime.monthOfYear().getAsText(), "Januar");
    }

    @Test
    public void rechnen() {
        DateTime dt0 = new DateTime(2009, 1, 29, 23, 01, 00, 00);
        MutableDateTime dt1 = dt0.toMutableDateTime();
        dt1.addDays(10);
        dt1.addYears(3);
        assertEquals(dt1.toString(), "2012-02-08T23:01:00.000+01:00");
    }

    @Test
    public void ausgabe() {
        DateTime dt = new DateTime("2009-01-29T23:14");
        assertEquals("2009-01-29", dt.toString("YYYY-MM-DD"));
        assertEquals("2009-01-29T23:14:00.000+01:00", dt.toString());
        assertEquals("29. Januar", dt.toString("d. MMMM"));

        assertEquals("29.01.2009", dt.toString(DateTimeFormat.mediumDate()));
        assertEquals("29. Januar 2009", dt.toString(DateTimeFormat.longDate()));
        assertEquals("29. janvier", dt.toString(DateTimeFormat.forPattern("d. MMMM").withLocale(Locale.FRENCH)));
    }

    @Test
    public void periodenUndIntervalle() {
        DateTime dt = new DateTime("2009-01-29T23:14");

        // Kalendarisch angegebene Zeitspanne -> Monate sind unterschiedlich lang
        Period p = new Period().plusMonths(1).plusDays(3);
        assertEquals("2009-03-03", dt.plus(p).toString(isodate));

        // Millisekunden genaue Zeitspanne
        Duration d = new Duration(24L * 60 * 60 * 1000);
        assertEquals("2009-01-30T23:14:00.000+01:00", dt.plus(d).toString());

        // Zwei Intervalle überschneiden sich - oder nicht
        DateTime dt0 = new DateTime("2009-01-28");
        DateTime dt1 = new DateTime("2009-01-29T14");
        DateTime dt2 = new DateTime("2009-01-29T18");
        DateTime dt3 = new DateTime("2009-01-30");
        Interval i_base = new Interval(dt0, dt2);
        Interval i_overlap = new Interval(dt1, dt3);
        Interval i_after = new Interval(dt2, dt3);

        assertTrue(i_base.overlaps(i_overlap));
        assertFalse(i_base.overlaps(i_after));
        assertEquals("2009-01-29T14:00:00.000/2009-01-30T00:00:00.000", i_overlap.toString());

        // Vergleiche unabhängig von der Zeitzone oder dem Kalender
        DateTime dt4 = new DateTime("2009-01-29T02:00:00", DateTimeZone.UTC);
        DateTime dt5 = new DateTime("2009-01-29T04:00:00", DateTimeZone.forOffsetHours(2));
        assertFalse(dt4 == dt5); // klar, ist ein Objektreferenz-Vergleich
        assertTrue(dt4.compareTo(dt5) == 0);
    }

}
