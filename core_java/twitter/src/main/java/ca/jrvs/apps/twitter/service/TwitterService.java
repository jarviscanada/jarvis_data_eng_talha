package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TwitterService implements Service {

    private CrdDao dao;

    //@Autowired
    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    /**
     * Validate and post a user input Tweet
     *
     * @param tweet tweet to be created
     * @return created tweet
     * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long out of range
     */
    @Override
    public Tweet postTweet(Tweet tweet) {
        //Get longitude and latitude values from tweet
        Double lon = tweet.getCoordinates().getCoordinates().get(0);
        Double lat = tweet.getCoordinates().getCoordinates().get(1);

        //Check to see if the values are valid and throw exception if invalid
        if (lon > 180.0 || lon < -180.0 || lat > 90.0 || lat < -90.0) {
            throw new IllegalArgumentException();
        }

        //Also check if tweet length is within our limit
        if (tweet.getText().length() > 140) {
            throw new IllegalArgumentException();
        }

        //finally just return the tweet object after creating it
        return (Tweet) dao.create(tweet);
    }

    /**
     * Search a tweet by ID
     *
     * @param id     tweet id
     * @param fields set fields not in the list to null
     * @return Tweet object which is returned by the Twitter API
     * @throws IllegalArgumentException if id or fields param is invalid
     */
    @Override
    public Tweet showTweet(String id, String[] fields) {
        //Check if given id is valid (if it is all digits)
        if (!Pattern.matches("^\\d+$", id)) {
            throw new IllegalArgumentException();
        }

        //Return tweet object found by that id
        return (Tweet) dao.findById(id);
    }

    /**
     * Delete Tweet(s) by id(s).
     *
     * @param ids tweet IDs which will be deleted
     * @return A list of Tweets
     * @throws IllegalArgumentException if one of the IDs is invalid.
     */
    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        List<Tweet> deleteTweets = new ArrayList<Tweet>();

        for (String id : ids) {
            //Need to check if id is valid
            if (!Pattern.matches("^\\d+$", id)) {
                throw new IllegalArgumentException();
            }

            deleteTweets.add((Tweet) dao.deleteById(id));
        }
        return deleteTweets;
    }
}