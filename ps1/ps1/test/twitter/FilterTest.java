/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");

        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }

    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");

        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));

        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }

    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));

        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    @Test
    public void testWrittenBySingleMatch() {
        Tweet tweet = new Tweet(1, "Alice", "Hello world", Instant.parse("2024-01-01T10:00:00Z"));
        List<Tweet> result = Filter.writtenBy(List.of(tweet), "alice");
        assertEquals(List.of(tweet), result);
    }

    @Test
    public void testWrittenByNoMatch() {
        Tweet tweet = new Tweet(1, "Bob", "Hello", Instant.now());
        List<Tweet> result = Filter.writtenBy(List.of(tweet), "alice");
        assertTrue(result.isEmpty());
    }
    @Test
    public void testInTimespanIncludesBoundary() {
        Tweet t1 = new Tweet(1, "A", "hi", Instant.parse("2024-01-01T10:00:00Z"));
        Tweet t2 = new Tweet(2, "B", "yo", Instant.parse("2024-01-01T12:00:00Z"));
        Timespan ts = new Timespan(Instant.parse("2024-01-01T10:00:00Z"),
                Instant.parse("2024-01-01T12:00:00Z"));
        List<Tweet> result = Filter.inTimespan(List.of(t1, t2), ts);
        assertEquals(List.of(t1, t2), result);
    }
    @Test
    public void testContainingMatch() {
        Tweet t1 = new Tweet(1, "A", "I love CS", Instant.now());
        Tweet t2 = new Tweet(2, "B", "Math is cool", Instant.now());
        List<Tweet> result = Filter.containing(List.of(t1, t2), List.of("cs", "math"));
        assertEquals(List.of(t1, t2), result);
    }

    @Test
    public void testContainingNoMatch() {
        Tweet t = new Tweet(1, "A", "hello world", Instant.now());
        List<Tweet> result = Filter.containing(List.of(t), List.of("java"));
        assertTrue(result.isEmpty());
    }
    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     *
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
