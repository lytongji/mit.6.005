/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.*;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     *
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet>selectedTweets=new ArrayList<>();
        for(Tweet tweet:tweets){
            if(tweet.getAuthor().equalsIgnoreCase(username)){
                selectedTweets.add(tweet);
            }
        }
        return selectedTweets;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     *
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        List<Tweet>selectedTweets=new ArrayList<>();
        for(Tweet tweet:tweets){
            if(tweet.getTimestamp().compareTo(timespan.getStart())>=0&&
            tweet.getTimestamp().compareTo(timespan.getEnd())<=0){
                selectedTweets.add(tweet);
            }
        }
        return selectedTweets;
    }

    /**
     * Find tweets that contain certain words.
     *
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets.
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when
     *         represented as a sequence of nonempty words bounded by space characters
     *         and the ends of the string) includes *at least one* of the words
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        Set<String> wordSet = new HashSet<>();
        for (String word : words) {
            wordSet.add(word.toLowerCase());
        }
        List<Tweet> selectedTweets=new ArrayList<>();
        for(Tweet tweet:tweets){
            String[] text=tweet.getText().toLowerCase().split("[^A-Za-z0-9_-]");
            for(String word:text){
                if(wordSet.contains(word)){
                    selectedTweets.add(tweet);
                    break;
                }
            }
        }
        return selectedTweets;
    }

}
