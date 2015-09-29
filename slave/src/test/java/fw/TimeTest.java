package fw;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeTest {

    @Test
    public void testOfMinutes() throws Exception {
        assertEquals(Time.ofMinutes(1).toTicks(), 20 * 60 * 1);
    }

    @Test
    public void testOfTicks() throws Exception {
        assertEquals(Time.ofTicks(85).toTicks(), 85);
    }

    @Test
    public void testOfSeconds() throws Exception {
        assertEquals(Time.ofSeconds(60).toTicks(), 20 * 60);
    }

    @Test
    public void testToMiliseconds() throws Exception {
        assertEquals(Time.ofSeconds(20).toMiliseconds(), 20L * 1000);
    }

    @Test
    public void testToSeconds() throws Exception {
        assertEquals(Time.ofMinutes(2).toSeconds(), 60L * 2);
    }

    @Test
    public void testToTicks() throws Exception {
        assertEquals(Time.ofTicks(24).toTicks(), 24);
    }

    @Test
    public void testToLongTicks() throws Exception {
        assertEquals(Time.ofTicks(50).toTicks(), 50L);
    }
}