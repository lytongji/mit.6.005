/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExtractTest {

    /*
     *test getTimespan:
     * partition:
     * item_num  :1,2,5
     * item_order:equal,partly equal,<,>,mixture
     *
     * test getMentionedUsername:
     * partition:
     * ite_num   :1,2
     * name_count:0,1,4
     * name_size :0(""),5,15(boundary),16(overflow)
     * name_dupic:no dupicated name,has duplicated name(different int capital)
     * capital   :all lowercase,all uppercase,mixture
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2017-02-17T10:00:00Z");
    private static final Instant d4 = Instant.parse("2018-02-17T11:00:00Z");
    private static final Instant d5 = Instant.parse("2019-02-17T10:00:00Z");
    private static final Instant d6 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet3 = new Tweet(1, "alyssa", "is it reas @liuyi @1234567890123456 rivest so much?", d3);
    private static final Tweet tweet4 = new Tweet(1, "alyssa", "is it rea@ to talk ab @123456789012345 rivest so much?", d4);
    private static final Tweet tweet5 = new Tweet(1, "alyssa", "is it rea @hello @HelLo to@fsfj talk about rivest so much?", d5);
    private static final Tweet tweet6 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);


    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);

    @Test
    public void testGetMentionedUsersTwoTweetsWithNoName() {
        Set<String> userNameSet= Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2));
        assertEquals("expect zero set size", 0, userNameSet.size());
    }
    @Test
    public void testGetMentionedUsersOneTweetsWithOneOverflowAndOneValidName() {
        Set<String> userNameSet= Extract.getMentionedUsers(List.of( tweet3));
        assertEquals("expect one set size", 1, userNameSet.size());
        Set<String> userNameSetCompare=new HashSet<>();
        userNameSetCompare.add("liuyi");
        assertEquals("expect one userName: liuyi", userNameSetCompare, userNameSet);
    }

    @Test
    public void testGetMentionedUsersWithBoundaryAndDuplicatedAndValidAndEmptyName() {
        Set<String> userNameSet= Extract.getMentionedUsers(Arrays.asList(tweet5, tweet4));
        assertEquals("expect one set size", 2, userNameSet.size());
        Set<String> userNameSetCompare=new HashSet<>();
        userNameSetCompare.add("hello");
        userNameSetCompare.add("123456789012345");
        assertEquals("expect one userName: hello,123456789012345", userNameSetCompare, userNameSet);
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));

        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    @Test
    public void testGetTimespanOneTweets() {
        Timespan timespan = Extract.getTimespan(List.of(tweet1));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    @Test
    public void testGetTimespanDuplicatedTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1,tweet6));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    @Test
    public void testGetTimespanAscendingTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1,tweet2,tweet3,tweet4));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d4, timespan.getEnd());
    }
    @Test
    public void testGetTimespanDescendingTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet4,tweet3,tweet2,tweet1));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d4, timespan.getEnd());
    }
    @Test
    public void testGetTimespanMixtureOrderTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2,tweet3,tweet4,tweet1));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d4, timespan.getEnd());
    }

    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));

        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     *
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
